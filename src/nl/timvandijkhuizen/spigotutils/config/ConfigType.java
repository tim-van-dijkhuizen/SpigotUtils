package nl.timvandijkhuizen.spigotutils.config;

import nl.timvandijkhuizen.spigotutils.menu.MenuItemClick;

public interface ConfigType<T> {
    
    /**
     * Deserializes and returns the value.
     * 
     * @param config
     * @param path
     * @return
     * @throws ConfigurationException
     */
    T getValue(YamlConfig config, ConfigOption<T> option) throws ConfigurationException;
    
    /**
     * Serializes and sets the value.
     * 
     * @param config
     * @param path
     * @param value
     */
    void setValue(YamlConfig config, ConfigOption<T> option, T value);
    
    /**
     * Returns the lore lines used
     * to display the current value.
     * 
     * @param config
     * @param option
     * @return
     */
    String getItemValue(YamlConfig config, ConfigOption<T> option);
    
    /**
     * Handle a click for this config type.
     * 
     * @param config
     * @param option
     * @return
     */
    void handleItemClick(YamlConfig config, ConfigOption<T> option, MenuItemClick event);

}
