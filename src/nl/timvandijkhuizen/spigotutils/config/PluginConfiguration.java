package nl.timvandijkhuizen.spigotutils.config;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;

public class PluginConfiguration extends YamlConfiguration implements OptionConfig {

    private File file;
    private boolean isLoaded;
    
    private Collection<ConfigOption<?>> options = new LinkedHashSet<>();

    public PluginConfiguration(PluginBase plugin) {
        this(plugin, "config.yml", "config.yml");
    }

    public PluginConfiguration(PluginBase plugin, String fileName, String defaults) {
        this(plugin, new File(plugin.getDataFolder(), fileName), defaults);
    }

    public PluginConfiguration(PluginBase plugin, File file, String defaults) {
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

    public boolean isLoaded() {
        return isLoaded;
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
    public Collection<ConfigOption<?>> getOptions() {
        return options;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> ConfigOption<T> getOption(String path) {
        return options.stream().filter(i -> i.getPath().equals(path)).map(i -> (ConfigOption<T>) i).findFirst().orElse(null);
    }

}