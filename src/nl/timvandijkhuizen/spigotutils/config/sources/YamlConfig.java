package nl.timvandijkhuizen.spigotutils.config.sources;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class YamlConfig extends YamlConfiguration implements OptionConfig {

    private File file;
    private Collection<ConfigOption<?>> options = new LinkedHashSet<>();

    public YamlConfig(PluginBase plugin) throws Throwable {
        this(plugin, "config.yml");
    }

    public YamlConfig(PluginBase plugin, String fileName) throws Throwable {
        this(plugin, new File(plugin.getDataFolder(), fileName), null);
    }
    
    public YamlConfig(PluginBase plugin, String fileName, String defaults) throws Throwable {
        this(plugin, new File(plugin.getDataFolder(), fileName), defaults);
    }

    public YamlConfig(PluginBase plugin, File file) throws Throwable {
        this(plugin, file, null);
    }
    
    public YamlConfig(PluginBase plugin, File file, String defaults) throws Throwable {
        this.file = file;

        if (file.exists()) {
            load(file);
        } else if(defaults != null) {
            plugin.saveResource(defaults, false);
        }
    }

    public <T> T get(String key, Class<T> type) {
        Object value = get(key);
        return type.cast(value);
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to save config " + file.getName() + ", error: " + e.getMessage());
        }
    }

    @Override
    public void addOption(ConfigOption<?> option) {
        options.add(option);
    }

    @Override
    public void addOptions(Collection<ConfigOption<?>> options) {
        options.addAll(options);
    }

    @Override
    public Collection<ConfigOption<?>> getOptions() {
        return options;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ConfigOption<T> getOption(String path) {
        return options.stream()
            .filter(i -> i.getPath().equals(path))
            .map(i -> (ConfigOption<T>) i)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public <T> T getOptionValue(String path) {
        ConfigOption<T> option = getOption(path);
        
        if(option == null) {
            throw new RuntimeException("Option does not exist");
        }
        
        return option.getValue(this);
    }
    
    @Override
    public <T> void setOptionValue(String path, T value) {
        ConfigOption<T> option = getOption(path);
        
        if(option == null) {
            throw new RuntimeException("Option does not exist");
        }
        
        option.setValue(this, value);
    }

    @Override
    public void setDefaultOptions() {
        for (ConfigOption<?> option : getOptions()) {
            if (option.isValueEmpty(this)) {
                option.resetValue(this);
            }
        }
    }

}