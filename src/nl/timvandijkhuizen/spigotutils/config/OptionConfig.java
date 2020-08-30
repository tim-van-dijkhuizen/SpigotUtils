package nl.timvandijkhuizen.spigotutils.config;

import java.util.Collection;

import org.bukkit.configuration.Configuration;

public interface OptionConfig extends Configuration {

    public void addOption(ConfigOption<?> option);
    
    public Collection<ConfigOption<?>> getOptions();
    
    public <T> ConfigOption<T> getOption(String path);
    
}
