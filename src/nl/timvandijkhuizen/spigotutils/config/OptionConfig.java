package nl.timvandijkhuizen.spigotutils.config;

import java.util.Collection;

public interface OptionConfig {

    public void addOption(ConfigOption<?> option);
    
    public Collection<ConfigOption<?>> getOptions();
    
    public <T> ConfigOption<T> getOption(String path);
    
}
