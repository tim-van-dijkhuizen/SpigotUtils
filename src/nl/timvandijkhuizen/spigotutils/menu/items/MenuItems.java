package nl.timvandijkhuizen.spigotutils.menu.items;

import org.bukkit.ChatColor;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.ui.UI;

public class MenuItems {

    public static final MenuItemBuilder BACK = new MenuItemBuilder(XMaterial.RED_BED.parseItem()).setName(UI.color("Go Back", UI.COLOR_SECONDARY, ChatColor.BOLD));
    public static final MenuItemBuilder CLOSE = new MenuItemBuilder(XMaterial.OAK_DOOR.parseItem()).setName(UI.color("Close", ChatColor.RED, ChatColor.BOLD));
    public static final MenuItemBuilder CANCEL = new MenuItemBuilder(XMaterial.GRAY_DYE.parseItem()).setName(UI.color("Cancel", ChatColor.GRAY, ChatColor.BOLD));
    public static final MenuItemBuilder SAVE = new MenuItemBuilder(XMaterial.LIME_DYE.parseItem()).setName(UI.color("Save", ChatColor.GREEN, ChatColor.BOLD));
    public static final MenuItemBuilder BACKGROUND = new MenuItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem());

    public static final MenuItemBuilder PREVIOUS = new MenuItemBuilder(XMaterial.ARROW.parseItem()).setName(UI.color("Previous Page", UI.COLOR_SECONDARY, ChatColor.BOLD));
    public static final MenuItemBuilder NEXT = new MenuItemBuilder(XMaterial.ARROW.parseItem()).setName(UI.color("Next Page", UI.COLOR_SECONDARY, ChatColor.BOLD));
    public static final MenuItemBuilder CURRENT = new MenuItemBuilder(XMaterial.MAP.parseItem());

}
