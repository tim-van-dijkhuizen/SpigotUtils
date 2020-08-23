package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.Map;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;

public class ConfigHelper {

    @SuppressWarnings("unchecked")
    public static <T> ConfigOption<T> getOption(Map<String, ConfigOption<?>> options, String path) {
        try {
            return (ConfigOption<T>) options.get(path);
        } catch(ClassCastException e) {
            return null;
        }
    }
    
}
