package nl.timvandijkhuizen.spigotutils.helpers;

import java.io.InputStream;

import nl.timvandijkhuizen.spigotutils.PluginBase;

public class ResourceHelper {

	public static InputStream getStream(String path) {
		return PluginBase.class.getClassLoader().getResourceAsStream(path);
	}
	
}
