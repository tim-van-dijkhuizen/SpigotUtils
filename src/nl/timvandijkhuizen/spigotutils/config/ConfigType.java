package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

public interface ConfigType<T> {
    
    /**
     * Deserializes and returns the value.
     * 
     * @param config
     * @param path
     * @return
     * @throws ConfigurationException
     */
    T getValue(Configuration config, ConfigOption<T> option);
    
    /**
     * Serializes and sets the value.
     * 
     * @param json
     * @param path
     * @param value
     */
    void setValue(Configuration config, ConfigOption<T> option, T value);
    
    /**
     * Deserializes and returns the value.
     * 
     * @param config
     * @param path
     * @return
     * @throws ConfigurationException
     */
    T getValue(JsonObject json, ConfigOption<T> option);
    
    /**
     * Serializes and sets the value.
     * 
     * @param json
     * @param path
     * @param value
     */
    void setValue(JsonObject json, ConfigOption<T> option, T value);
    
    /**
     * Returns the lore lines used
     * to display the current value.
     * 
     * @param config
     * @param option
     * @return
     */
    String getValueLore(T value);
    
    /**
     * Returns whether the value is empty.
     * 
     * @param config
     * @param option
     * @return
     */
    boolean isValueEmpty(T value);
    
    /**
     * Returns the input value for this option.
     * 
     * @param config
     * @param option
     * @return
     */
    void getValueInput(Player player, Consumer<T> callback);

}
