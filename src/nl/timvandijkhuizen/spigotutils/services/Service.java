package nl.timvandijkhuizen.spigotutils.services;

public interface Service {

    /**
     * Returns the handle of the service.
     * 
     * @return
     */
    public String getHandle();

    /**
     * Called when the service is created.
     * 
     * @throws Throwable
     */
    public void init() throws Throwable;

    /**
     * Called when the service is loaded.
     * 
     * @throws Throwable
     */
    public void load() throws Throwable;

    /**
     * Called when the service is unloaded.
     * 
     * @throws Throwable
     */
    public void unload() throws Throwable;

}
