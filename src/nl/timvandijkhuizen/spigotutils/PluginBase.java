package nl.timvandijkhuizen.spigotutils;

import java.util.HashMap;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.timvandijkhuizen.spigotutils.services.Service;

@SuppressWarnings("unchecked")
public abstract class PluginBase extends JavaPlugin {

    private static PluginBase instance;
    private HashMap<String, Service> services = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        
        try {
            load();

            for (Service service : registerServices()) {
                loadService(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void onDisable() {
        try {
            for (Service service : services.values()) {
                unloadService(service);
            }

            unload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the plugin is loaded. Services are loaded after this.
     * 
     * @throws Exception
     */
    public abstract void load() throws Exception;

    /**
     * Called when the plugin is unloaded. Services are unloaded before this.
     * 
     * @throws Exception
     */
    public abstract void unload() throws Exception;

    /**
     * Returns the instance of this plugin. Extending plugins
     * will need to add their own static instance method to
     * be able to access their own methods.
     * 
     * @return
     */
    public static PluginBase getInstance() {
        return instance;
    }
    
    /**
     * Returns all registered services.
     * 
     * @return
     * @throws Exception
     */
    public abstract Service[] registerServices() throws Exception;

    /**
     * Loads a service.
     * 
     * @param service
     */
    private void loadService(Service service) {
        services.put(service.getHandle(), service);

        // Load service
        try {
            service.load();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Register as listener
        if (service instanceof Listener) {
            getServer().getPluginManager().registerEvents((Listener) service, this);
        }
    }

    /**
     * Unloads a service.
     * 
     * @param service
     */
    private void unloadService(Service service) {
        try {
            service.unload();
        } catch (Exception e) {
            e.printStackTrace();
        }

        services.remove(service);
    }

    /**
     * Returns a service by its handle.
     * 
     * @param handle
     * @return
     */
    public <T extends Service> T getService(String handle) {
        try {
            return (T) services.get(handle);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

}
