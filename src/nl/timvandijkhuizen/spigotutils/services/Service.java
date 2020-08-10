package nl.timvandijkhuizen.spigotutils.services;

public interface Service {

	public String getHandle();
	public void load() throws Exception;
	public void unload() throws Exception;
	
}
