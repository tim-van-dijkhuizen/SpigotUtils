package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import nl.timvandijkhuizen.spigotutils.ui.UI;

public class MenuItems {

    public static final MenuItemBuilder BACK = new MenuItemBuilder(Material.RED_BED).setName(UI.color("Go back", UI.COLOR_SECONDARY, ChatColor.BOLD));
    public static final MenuItemBuilder CLOSE = new MenuItemBuilder(Material.OAK_DOOR).setName(UI.color("Close", ChatColor.RED, ChatColor.BOLD));
    public static final MenuItemBuilder CANCEL = new MenuItemBuilder(Material.GRAY_DYE).setName(UI.color("Cancel", ChatColor.GRAY, ChatColor.BOLD));
    public static final MenuItemBuilder SAVE = new MenuItemBuilder(Material.LIME_DYE).setName(UI.color("Save", ChatColor.GREEN, ChatColor.BOLD));
    public static final MenuItemBuilder BACKGROUND = new MenuItemBuilder(Material.GRAY_STAINED_GLASS_PANE);
    
    public static final MenuItemBuilder PREVIOUS = new MenuItemBuilder(Material.ARROW).setName(UI.color("Previous page", UI.COLOR_SECONDARY, ChatColor.BOLD));
    public static final MenuItemBuilder NEXT = new MenuItemBuilder(Material.ARROW).setName(UI.color("Next page", UI.COLOR_SECONDARY, ChatColor.BOLD));
    public static final MenuItemBuilder CURRENT = new MenuItemBuilder(Material.MAP);
    
}
