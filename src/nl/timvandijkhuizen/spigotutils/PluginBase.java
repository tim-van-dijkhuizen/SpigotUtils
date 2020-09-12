package nl.timvandijkhuizen.spigotutils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;
import nl.timvandijkhuizen.spigotutils.services.Service;

@SuppressWarnings("unchecked")
public abstract class PluginBase extends JavaPlugin {

    private static PluginBase instance;
    
    private Map<String, Service> services = new HashMap<>();
    private Map<String, String> serviceErrors = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        
        try {
            init();
            load();

            // Initiate and load services
            for (Service service : registerServices()) {
                services.put(service.getHandle(), service);
                service.init();
                loadService(service);
            }
            
            ready();
        } catch (Exception e) {
            ConsoleHelper.printError("Failed to load plugin.", e);
        }
    }

    @Override
    public void onDisable() {
        for (Service service : services.values()) {
            unloadService(service);
        }

       try {
            unload();
        } catch (Exception e) {
            ConsoleHelper.printError("Failed to unload plugin.", e);
        }
    }

    /**
     * Called when the plugin is created.
     * 
     * @throws Exception
     */
    public void init() throws Exception { }
    
    /**
     * Called when the plugin is loaded. Services are loaded after this.
     * 
     * @throws Exception
     */
    public void load() throws Exception { }

    /**
     * Called after the plugin and its services have been loaded.
     * 
     * @throws Exception
     */
    public void ready() throws Exception { }
    
    /**
     * Called when the plugin is unloaded. Services are unloaded before this.
     * 
     * @throws Exception
     */
    public void unload() throws Exception { }

    /**
     * Reloads all services.
     */
    public void reload() {
        try {
            unload();
        } catch(Exception e) {
            ConsoleHelper.printError("Failed to unload plugin.", e);
        }
        
        for (Service service : services.values()) {
            reloadService(service);
        }
        
        try {
            load();
        } catch(Exception e) {
            ConsoleHelper.printError("Failed to load plugin.", e);
        }
    }
    
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
        try {
            service.load();
            
            // Register as listener
            if (service instanceof Listener) {
                getServer().getPluginManager().registerEvents((Listener) service, this);
            }
        } catch (Exception e) {
            ConsoleHelper.printError("Failed to load service: " + service.getHandle(), e);
            serviceErrors.put(service.getHandle(), e.getMessage());
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
            ConsoleHelper.printError("Failed to unload service: " + service.getHandle(), e);
        }
    }
    
    /**
     * Reloads a service.
     * 
     * @param service
     */
    private void reloadService(Service service) {
        serviceErrors.remove(service.getHandle());
        
        try {
            service.unload();
            service.load();
        } catch (Exception e) {
            ConsoleHelper.printError("Failed to reload service: " + service.getHandle(), e);
            serviceErrors.put(service.getHandle(), e.getMessage());
        }
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
    
    public Map<String, String> getServiceErrors() {
        return serviceErrors;
    }
    
    public String getServiceError(String handle) {
        return serviceErrors.get(handle);
    }

}
