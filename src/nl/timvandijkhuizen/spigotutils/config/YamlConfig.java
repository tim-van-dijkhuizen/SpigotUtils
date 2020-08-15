package nl.timvandijkhuizen.spigotutils.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.timvandijkhuizen.spigotutils.PluginBase;

public class YamlConfig extends YamlConfiguration {

    private PluginBase plugin;
    private File file;
    private boolean isLoaded;

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
            throw new ConfigurationException("Failed to load configuration file: " + e.getMessage());
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public <T> T get(String key, Class<T> type) {
        Object value = get(key);

        for (ConfigConverter<?> converter : plugin.getConfigConverters()) {
            if (converter.getType() != type) {
                continue;
            }

            return type.cast(converter.deserialize(value));
        }

        return type.cast(value);
    }

    @Override
    public void set(String key, Object object) {
        Object value = object;

        for (ConfigConverter<?> converter : plugin.getConfigConverters()) {
            if (converter.getType() != object.getClass()) {
                continue;
            }

            value = converter.serializeObject(object);
        }

        super.set(key, value);
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to save config " + file.getName() + ", error: " + e.getMessage());
        }
    }

}