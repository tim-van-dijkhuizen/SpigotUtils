package nl.timvandijkhuizen.spigotutils.config;

import nl.timvandijkhuizen.spigotutils.menu.MenuItemBuilder;

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
     * Creates a menu item for this option.
     * 
     * @param currentValue
     * @return
     */
    MenuItemBuilder createMenuItem(YamlConfig config, ConfigOption<T> option);

}
