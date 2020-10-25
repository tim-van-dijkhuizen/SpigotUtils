package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import org.bukkit.Material;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.data.DataArguments;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;

public class ConfigOption<T> {

    private String path;
    private String name;
    private Material icon;
    private ConfigType<T> type;

    private T defaultValue = null;
    private boolean required = false;
    private DataArguments meta = new DataArguments();

    public ConfigOption(String path, String name, ConfigType<T> type) {
        this(path, name, XMaterial.COMPARATOR, type);
    }

    public ConfigOption(String path, String name, Material icon, ConfigType<T> type) {
        this.path = path;
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public ConfigOption(String path, String name, XMaterial icon, ConfigType<T> type) {
        this(path, name, icon.parseMaterial(true), type);
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
        if (type.isValueEmpty(config, this)) {
            return defaultValue;
        }

        return type.getValue(config, this);
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
     * Serializes T to a String.
     * 
     * @param value
     * @return
     */
    public String getRawValue(OptionConfig config) {
        return type.getRawValue(config, this);
    }
    
    /**
     * Returns the value lore.
     * 
     * @param config
     * @return
     */
    public String getDisplayValue(OptionConfig config) {
        return type.getDisplayValue(config, this);
    }

    /**
     * Returns whether the value is empty.
     * 
     * @param config
     * @return
     */
    public boolean isValueEmpty(OptionConfig config) {
        return type.isValueEmpty(config, this);
    }

    /**
     * Asks the specified player for input.
     * 
     * @param player
     * @param value
     * @param callback
     */
    public void getValueInput(OptionConfig config, MenuItemClick event, Consumer<T> callback) {
        type.getValueInput(config, this, event, callback);
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
     * Returns the option's meta data. This data can be used to store more info
     * per option, for example a description.
     * 
     * @return
     */
    public DataArguments getMeta() {
        return meta;
    }

}
