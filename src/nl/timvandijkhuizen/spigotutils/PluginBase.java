package nl.timvandijkhuizen.spigotutils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;
import nl.timvandijkhuizen.spigotutils.services.Service;

public abstract class PluginBase extends JavaPlugin {

    private Map<String, Service> services = new HashMap<>();
    private Map<String, String> serviceErrors = new HashMap<>();

    @Override
    public void onLoad() {
        try {
            for (Service service : registerServices()) {
                services.put(service.getHandle(), service);
                ConsoleHelper.printDebug("Registered service " + service.getHandle());
            }
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to register services.", e);
        }
        
        try {
            setup();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to setup plugin.", e);
        }
    }
    
    @Override
    public void onEnable() {
        try {
            init();

            for (Service service : services.values()) {
                service.init();
                ConsoleHelper.printDebug("Initialized service " + service.getHandle());
            }
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to initialize plugin or service.", e);
        }

        try {
            load();

            for (Service service : services.values()) {
                loadService(service);
                ConsoleHelper.printDebug("Loaded service " + service.getHandle());
            }

            ready();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to load plugin or service.", e);
        }
    }

    @Override
    public void onDisable() {
        for (Service service : services.values()) {
            unloadService(service);
        }

        try {
            unload();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to unload plugin.", e);
        }
    }

    /**
     * Called during onLoad.
     * 
     * @throws Throwable
     */
    public void setup() throws Throwable {
    }
    
    /**
     * Called when the plugin is created.
     * 
     * @throws Throwable
     */
    public void init() throws Throwable {
    }

    /**
     * Called when the plugin is loaded. Services are loaded after this.
     * 
     * @throws Throwable
     */
    public void load() throws Throwable {
    }

    /**
     * Called after the plugin and its services have been loaded.
     * 
     * @throws Throwable
     */
    public void ready() throws Throwable {
    }

    /**
     * Called when the plugin is unloaded. Services are unloaded before this.
     * 
     * @throws Throwable
     */
    public void unload() throws Throwable {
    }

    /**
     * Reloads all services.
     */
    public void reload() {
        try {
            unload();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to unload plugin.", e);
        }

        for (Service service : services.values()) {
            reloadService(service);
        }

        try {
            load();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to load plugin.", e);
        }
    }

    /**
     * Returns all registered services.
     * 
     * @return
     * @throws Throwable
     */
    public Service[] registerServices() throws Throwable {
        return new Service[] {};
    }

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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
            ConsoleHelper.printError("Service with handle " + handle + " cannot be cast to the specified type.");
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
