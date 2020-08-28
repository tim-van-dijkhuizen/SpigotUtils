package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

public class ConfigOption<T> {

    private String path;
    private ConfigType<T> type;
    
    private T defaultValue;
    private ConfigIcon icon;
    private boolean readOnly;
    private boolean required;
    
    public ConfigOption(String path, ConfigType<T> type) {
        this.path = path;
        this.type = type;
    }
    
    public ConfigOption<T> setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
    
    public ConfigOption<T> setIcon(ConfigIcon icon) {
        this.icon = icon;
        return this;
    }
    
    public ConfigOption<T> setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }
    
    public ConfigOption<T> setRequired(boolean required) {
        this.required = required;
        return this;
    }
    
    /**
     * Returns this option's value.
     * 
     * @param config
     * @return
     */
    public T getValue(Configuration config) {
        if(!config.contains(path)) {
            return defaultValue;
        }
        
        // Get value
        T value = type.getValue(config, this);
        
        if(value == null) {
            return defaultValue;
        }
        
        return value;
    }
    
    /**
     * Sets this option's value.
     * 
     * @param config
     * @param value
     */
    public void setValue(Configuration config, T value) {
        type.setValue(config, this, value);
    }
    
    /**
     * Returns this option's value.
     * 
     * @param json
     * @return
     */
    public T getValue(JsonObject json) {
        T value = type.getValue(json, this);
        
        if(value == null) {
            return defaultValue;
        }
        
        return value;
    }
    
    /**
     * Sets this option's value.
     * 
     * @param json
     * @param value
     */
    public void setValue(JsonObject json, T value) {
        type.setValue(json, this, value);
    }
    
    /**
     * Returns the value lore.
     * 
     * @param config
     * @return
     */
    public String getValueLore(Configuration config) {
        return type.getValueLore(getValue(config));
    }

    /**
     * Returns whether the value is empty.
     * 
     * @param config
     * @return
     */
    public boolean isValueEmpty(Configuration config) {
        return type.isValueEmpty(getValue(config));
    }
    
    public void getValueInput(Player player, Consumer<T> callback) {
        type.getValueInput(player, callback);
    }
    
    /**
     * Returns the option path.
     * 
     * @return
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Returns the default value.
     * 
     * @return
     */
    public T getDefaultValue() {
        return defaultValue;
    }
    
    /**
     * Returns the icon. This may be null.
     * 
     * @return
     */
    public ConfigIcon getIcon() {
        return icon;
    }
    
    /**
     * This returns whether this option is read-only.
     * 
     * @return
     */
    public boolean isReadOnly() {
        return readOnly;
    }
    
    /**
     * This returns whether this option is required.
     * 
     * @return
     */
    public boolean isRequired() {
        return required;
    }
    
}
