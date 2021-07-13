package nl.timvandijkhuizen.spigotutils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;
import nl.timvandijkhuizen.spigotutils.services.Service;

public abstract class PluginBase extends JavaPlugin {

    private Map<Class<? extends Service>, Service> services = new HashMap<>();
    private Map<Class<? extends Service>, String> serviceErrors = new HashMap<>();

    @Override
    public void onLoad() {
        try {
            for (Service service : registerServices()) {
                services.put(service.getClass(), service);
                ConsoleHelper.printDebug("Registered service " + service.getClass().getSimpleName());
            }
        } catch (Throwable t) {
            ConsoleHelper.printError("Failed to register services.", t);
        }
        
        // Set up plug-in
        try {
            setup();
        } catch (Throwable t) {
            ConsoleHelper.printError("Failed to set up plug-in.", t);
        }
    }
    
    @Override
    public void onEnable() {
        try {
            init();
        } catch (Throwable t) {
            ConsoleHelper.printError("Failed to initialize plug-in.", t);
        }

        // Initialize services
        for (Service service : services.values()) {
            try {
                service.init();
                ConsoleHelper.printDebug("Initialized service " + service.getClass().getSimpleName());
            } catch (Throwable t) {
                ConsoleHelper.printError("Failed to initialize service.", t);
            }
        }
        
        // Load plug-in
        try {
            load();
        } catch (Throwable t) {
            ConsoleHelper.printError("Failed to load plug-in.", t);
        }
        
        // Load services
        for (Service service : services.values()) {
            loadService(service);
        }
        
        // Plug-in ready
        try {
            ready();
        } catch (Throwable t) {
            ConsoleHelper.printError("Failed to call plug-in ready.", t);
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
            ConsoleHelper.printError("Failed to unload plug-in.", e);
        }
        
        // Destroy services
        for (Service service : services.values()) {
            try {
                service.destroy();
            } catch (Throwable t) {
                ConsoleHelper.printError("Failed to destroy service.", t);
            }
        }
        
        // Destroy plug-in
        try {
            destroy();
        } catch (Throwable t) {
            ConsoleHelper.printError("Failed to unload plug-in.", t);
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
     * Called when the plugin is destroyed.
     * 
     * @throws Throwable
     */
    public void destroy() throws Throwable {
    }
    
    /**
     * Reloads all services.
     */
    public void reload() {
        try {
            unload();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to unload plug-in.", e);
        }

        for (Service service : services.values()) {
            reloadService(service);
        }

        try {
            load();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to load plug-in.", e);
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
            
            ConsoleHelper.printDebug("Loaded service " + service.getClass().getSimpleName());
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to load service: " + service.getClass().getSimpleName(), e);
            serviceErrors.put(service.getClass(), e.getMessage());
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
            ConsoleHelper.printDebug("Unloaded service " + service.getClass().getSimpleName());
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to unload service: " + service.getClass().getSimpleName(), e);
        }
    }

    /**
     * Reloads a service.
     * 
     * @param service
     */
    private void reloadService(Service service) {
        serviceErrors.remove(service.getClass());

        try {
            service.unload();
            service.load();
        } catch (Throwable e) {
            ConsoleHelper.printError("Failed to reload service: " + service.getClass().getSimpleName(), e);
            serviceErrors.put(service.getClass(), e.getMessage());
        }
    }

    /**
     * Returns a service by its class.
     * 
     * @param class
     * @return
     */
    public <T extends Service> T getService(Class<T> service) {
        return service.cast(services.get(service));
    }
    
    public Map<Class<? extends Service>, String> getAllServiceErrors() {
        return serviceErrors;
    }

    public String getServiceErrors(Class<? extends Service> service) {
        return serviceErrors.get(service);
    }

}
