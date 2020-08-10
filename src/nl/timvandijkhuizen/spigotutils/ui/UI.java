package nl.timvandijkhuizen.spigotutils.ui;

import org.bukkit.ChatColor;

public class UI {

	public static final ChatColor PRIMARY_COLOR = ChatColor.GREEN;
	public static final ChatColor SECONDARY_COLOR = ChatColor.AQUA;
	public static final ChatColor TEXT_COLOR = ChatColor.GRAY;
	public static final ChatColor ERROR_COLOR = ChatColor.RED;
	
	public static String color(String text, ChatColor... colors) {
		String colorString = "" + ChatColor.RESET;
		
		for(ChatColor color : colors) {
			colorString += color;
		}
		
		return colorString + text;
	}
	
}
