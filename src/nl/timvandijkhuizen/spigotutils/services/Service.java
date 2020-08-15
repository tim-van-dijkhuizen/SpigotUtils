package nl.timvandijkhuizen.spigotutils.services;

public interface Service {

    /**
     * Returns the handle of the service.
     * 
     * @return
     */
    public String getHandle();

    /**
     * Called when the service is loaded.
     * 
     * @throws Exception
     */
    public void load() throws Exception;

    /**
     * Called when the service is unloaded.
     * 
     * @throws Exception
     */
    public void unload() throws Exception;

}
