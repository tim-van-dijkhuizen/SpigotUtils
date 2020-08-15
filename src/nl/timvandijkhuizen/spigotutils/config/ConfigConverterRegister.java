package nl.timvandijkhuizen.spigotutils.config;

import java.util.Collection;

public interface ConfigConverterRegister {

    public Collection<ConfigConverter<?>> getConfigConverters();

}
