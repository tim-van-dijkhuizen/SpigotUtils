package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.Collection;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;

public class ConfigHelper {

    @SuppressWarnings("unchecked")
    public static <T> ConfigOption<T> getOption(Collection<ConfigOption<?>> options, String path) {
        try {
            return (ConfigOption<T>) options.stream().filter(i -> i.getPath() == path).findFirst().orElse(null);
        } catch(ClassCastException e) {
            return null;
        }
    }
    
}
