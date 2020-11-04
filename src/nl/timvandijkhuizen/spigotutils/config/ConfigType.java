package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import javax.naming.ConfigurationException;

import nl.timvandijkhuizen.spigotutils.menu.MenuClick;

public interface ConfigType<T> {
    
    /**
     * Deserializes and returns the value.
     * 
     * @param config
     * @param path
     * @return
     * @throws ConfigurationException
     */
    T getValue(OptionConfig config, ConfigOption<T> option);
    
    /**
     * Serializes and sets the value.
     * 
     * @param json
     * @param path
     * @param value
     */
    void setValue(OptionConfig config, ConfigOption<T> option, T value);
    
    /**
     * Returns the raw value.
     * 
     * @param config
     * @param option
     * @return
     */
    String getRawValue(OptionConfig config, ConfigOption<T> option);

    /**
     * Returns the display value.
     * 
     * @param value
     * @return
     */
    String getDisplayValue(OptionConfig config, ConfigOption<T> option);
    
    /**
     * Returns whether the value is empty.
     * 
     * @param config
     * @param option
     * @return
     */
    boolean isValueEmpty(OptionConfig config, ConfigOption<T> option);
    
    /**
     * Returns the input value for this option.
     * 
     * @param config
     * @param value
     * @param option
     * @return
     */
    void getValueInput(OptionConfig config, ConfigOption<T> option, MenuClick event, Consumer<T> callback);

}
