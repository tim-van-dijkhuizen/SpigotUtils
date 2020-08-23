package nl.timvandijkhuizen.spigotutils.config;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.helpers.ConfigHelper;
import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;

public class YamlConfig extends YamlConfiguration {

    private PluginBase plugin;
    private File file;
    private boolean isLoaded;
    
    private Map<String, ConfigOption<?>> options = new LinkedHashMap<>();

    public YamlConfig(PluginBase plugin) {
        this(plugin, "config.yml", "config.yml");
    }

    public YamlConfig(PluginBase plugin, String fileName, String defaults) {
        this(plugin, new File(plugin.getDataFolder(), fileName), defaults);
    }

    public YamlConfig(PluginBase plugin, File file, String defaults) {
        this.plugin = plugin;
        this.file = file;

        if (!file.exists()) {
            plugin.saveResource(defaults, false);
        }

        try {
            load(file);
            isLoaded = true;
        } catch (IOException | InvalidConfigurationException e) {
            ConsoleHelper.printError("Failed to load configuration file: " + e.getMessage(), e);
        }
    }
    
    public PluginBase getPlugin() {
        return plugin;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public <T> T get(String key, Class<T> type) {
        Object value = get(key);
        return type.cast(value);
    }
    
    public void addOption(ConfigOption<?> option) {
        options.put(option.getPath(), option);
    }
    
    public Collection<ConfigOption<?>> getOptions() {
        return options.values();
    }
    
    public ConfigOption<?> getOption(String path) {
        return options.get(path);
    }
    
    public <T> T getOptionValue(ConfigOption<T> option) {
        return option.getValue(this);
    }
    
    public <T> T getOptionValue(String path) {
        ConfigOption<T> option = ConfigHelper.getOption(options, path);
        
        if(option == null) {
            throw new RuntimeException("No option with path " + path + " exists");
        }
        
        return getOptionValue(option);
    }

    public <T> void setOptionValue(ConfigOption<T> option, T value) {
        option.setValue(this, value);
        this.save();
    }
    
    public <T> void setOptionValue(String path, T value) {
        ConfigOption<T> option = ConfigHelper.getOption(options, path);
        
        if(option == null) {
            throw new RuntimeException("No option with path " + path + " exists");
        }
        
        setOptionValue(option, value);
    }
    
    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to save config " + file.getName() + ", error: " + e.getMessage());
        }
    }

}