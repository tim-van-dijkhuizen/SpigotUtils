package nl.timvandijkhuizen.spigotutils.config;

import java.util.Collection;

import org.bukkit.configuration.Configuration;

public interface OptionConfig extends Configuration {

    public void addOption(ConfigOption<?> option);

    public void addOptions(Collection<ConfigOption<?>> options);

    public Collection<ConfigOption<?>> getOptions();

    public <T> ConfigOption<T> getOption(String path);

    public void setDefaultOptions();

}
