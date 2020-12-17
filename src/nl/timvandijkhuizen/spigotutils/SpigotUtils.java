package nl.timvandijkhuizen.spigotutils;

public class SpigotUtils extends PluginBase {

    private static SpigotUtils instance;
    
    @Override
    public void init() throws Throwable {
        instance = this;
    }
    
    public static SpigotUtils getInstance() {
        return instance;
    }

}
