package nl.timvandijkhuizen.spigotutils;

import nl.timvandijkhuizen.spigotutils.services.Service;

public class SpigotUtils extends PluginBase {

    private static SpigotUtils instance;
    
    @Override
    public void init() throws Throwable {
        instance = this;
    }
    
    @Override
    public Service[] registerServices() throws Throwable {
        return new Service[] {};
    }
    
    public static SpigotUtils getInstance() {
        return instance;
    }

}
