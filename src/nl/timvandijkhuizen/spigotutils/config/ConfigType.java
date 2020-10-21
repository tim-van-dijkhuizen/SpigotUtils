package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import javax.naming.ConfigurationException;

import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;

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
     * Returns the lore lines used to display the current value.
     * 
     * @param config
     * @param option
     * @return
     */
    String getValueLore(OptionConfig config, ConfigOption<T> option);

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
    void getValueInput(OptionConfig config, ConfigOption<T> option, MenuItemClick event, Consumer<T> callback);

}
