package nl.timvandijkhuizen.spigotutils.config.types;

import org.bukkit.ChatColor;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class ConfigTypeMessage extends ConfigTypeString {

    @Override
    public String getValue(OptionConfig config, ConfigOption<String> option) {
    	String raw = super.getValue(config, option);
    	
    	if(raw == null) {
    		return null;
    	}
    	
    	return ChatColor.translateAlternateColorCodes('&', raw);
    }
	
}
