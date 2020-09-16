package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.data.DataArguments;

public class ConfigOption<T> {

    private String path;
    private String name;
    private Material icon;
    private ConfigType<T> type;
    
    private T defaultValue = null;
    private boolean required = false;
    private DataArguments meta = new DataArguments();
    
    public ConfigOption(String path, String name, ConfigType<T> type) {
        this(path, name, Material.COMPARATOR, type);
    }
    
    public ConfigOption(String path, String name, Material icon, ConfigType<T> type) {
        this.path = path;
        this.name = name;
        this.icon = icon;
        this.type = type;
    }
    
    /**
     * Sets the default value.
     * 
     * @param defaultValue
     * @return
     */
    public ConfigOption<T> setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
    
    /**
     * Sets whether this option is required.
     * 
     * @param required
     * @return
     */
    public ConfigOption<T> setRequired(boolean required) {
        this.required = required;
        return this;
    }
    
    /**
     * Sets the option meta.
     * 
     * @param meta
     */
    public ConfigOption<T> setMeta(DataArguments meta) {
        this.meta = meta;
        return this;
    }
    
    /**
     * Returns this option's value.
     * 
     * @param config
     * @return
     */
    public T getValue(OptionConfig config) {
        T value = type.getValue(config, this);
        
        if(!config.contains(path) || type.isValueEmpty(value)) {
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
    public void setValue(OptionConfig config, T value) {
        type.setValue(config, this, value);
    }
    
    /**
     * Sets the value to the default value.
     * 
     * @param config
     */
    public void resetValue(OptionConfig config) {
        setValue(config, defaultValue);
    }
    
    /**
     * Returns the value lore.
     * 
     * @param config
     * @return
     */
    public String getValueLore(OptionConfig config) {
        return type.getValueLore(getValue(config));
    }

    /**
     * Returns whether the value is empty.
     * 
     * @param config
     * @return
     */
    public boolean isValueEmpty(OptionConfig config) {
        T rawValue = type.getValue(config, this);
        return !config.contains(path) || type.isValueEmpty(rawValue);
    }
    
    /**
     * Asks the specified player for input.
     * 
     * @param player
     * @param value
     * @param callback
     */
    public void getValueInput(Player player, T value, Consumer<T> callback) {
        type.getValueInput(player, value, callback);
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
     * Returns the name.
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the icon.
     * 
     * @return
     */
    public Material getIcon() {
        return icon;
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
     * This returns whether this option is required.
     * 
     * @return
     */
    public boolean isRequired() {
        return required;
    }
    
    /**
     * Returns the option's meta data.
     * This data can be used to store more info
     * per option, for example a description.
     * 
     * @return
     */
    public DataArguments getMeta() {
        return meta;
    }
    
}
