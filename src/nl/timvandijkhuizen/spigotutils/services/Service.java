package nl.timvandijkhuizen.spigotutils.services;

public interface Service {

    /**
     * Returns the handle of the service.
     * 
     * @return
     */
    @Deprecated
    public default String getHandle() {
        return null;
    }

    /**
     * Called when the service is created.
     * 
     * @throws Throwable
     */
    public default void init() throws Throwable {
        
    }

    /**
     * Called when the service is loaded.
     * 
     * @throws Throwable
     */
    public default void load() throws Throwable {
        
    }

    /**
     * Called when the service is unloaded.
     * 
     * @throws Throwable
     */
    public default void unload() throws Throwable {
        
    }
    
    /**
     * Called when the service is destroyed.
     * 
     * @throws Throwable
     */
    public default void destroy() throws Throwable {
        
    }

}
