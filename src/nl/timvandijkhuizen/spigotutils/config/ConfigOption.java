package nl.timvandijkhuizen.spigotutils.config;

import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;

public class ConfigOption<T> {

    private String path;
    private ConfigType<T> type;
    private T defaultValue;
    private ConfigIcon icon;
    
    public ConfigOption(String path, ConfigType<T> type) {
        this.path = path;
        this.type = type;
    }
    
    public ConfigOption(String path, ConfigType<T> type, ConfigIcon icon) {
        this(path, type);
        this.icon = icon;
    }
    
    public ConfigOption(String path, ConfigType<T> type, T defaultValue) {
        this(path, type);
        this.defaultValue = defaultValue;
    }
    
    public ConfigOption(String path, ConfigType<T> type, T defaultValue, ConfigIcon icon) {
        this(path, type, defaultValue);
        this.icon = icon;
    }
    
    /**
     * Returns this option's value.
     * 
     * @param config
     * @return
     */
    public T getValue(YamlConfig config) {
        if(!config.contains(path, true)) {
            return defaultValue;
        }
        
        try {
            return type.getValue(config, this);
        } catch(ConfigurationException e) {
            ConsoleHelper.printError("Invalid config value for \"" + path + "\", using default value.");
            return defaultValue;
        }
    }
    
    /**
     * Sets this option's value.
     * 
     * @param config
     * @param value
     */
    public void setValue(YamlConfig config, T value) {
        type.setValue(config, this, value);
    }
    
    public String getPath() {
        return path;
    }
    
    public ConfigType<T> getType() {
        return type;
    }
    
    public T getDefaultValue() {
        return defaultValue;
    }
    
    public ConfigIcon getIcon() {
        return icon;
    }
    
}
