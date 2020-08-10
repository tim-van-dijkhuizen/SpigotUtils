package nl.timvandijkhuizen.spigotutils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.timvandijkhuizen.spigotutils.config.ConfigConverter;
import nl.timvandijkhuizen.spigotutils.config.ConfigConverterRegister;
import nl.timvandijkhuizen.spigotutils.services.Service;

@SuppressWarnings("unchecked")
public abstract class PluginBase extends JavaPlugin implements ConfigConverterRegister {

	private HashMap<String, Service> services = new HashMap<>();
	private Collection<ConfigConverter<?>> converters = new HashSet<>();
	
	@Override
	public void onEnable() {
		try {
			load();
			
			for(Service service : registerServices()) {
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
			for(Service service : services.values()) {
				unloadService(service);
			}
			
			unload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void load() throws Exception;
	public abstract void unload() throws Exception;
	
	public abstract Service[] registerServices() throws Exception;
	public abstract ConfigConverter<?>[] registerConfigConverters() throws Exception;
	
	private void loadService(Service service) {
		services.put(service.getHandle(), service);
		
		// Load service
		try {
			service.load();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Register as listener
		if(service instanceof Listener) {
			getServer().getPluginManager().registerEvents((Listener) service, this);
		}
	}
	
	private void unloadService(Service service) {
		try {
			service.unload();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		services.remove(service);
	}
	
	public <T extends Service> T getService(String handle) {
		try {
			return (T) services.get(handle);
		} catch(ClassCastException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Collection<ConfigConverter<?>> getConfigConverters() {
		return converters;
	}
	
}
