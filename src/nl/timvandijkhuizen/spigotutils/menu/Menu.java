package nl.timvandijkhuizen.spigotutils.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import nl.timvandijkhuizen.spigotutils.ui.UI;

public class Menu {

	public static final MenuItemBuilder BACK_BUTTON = new MenuItemBuilder(Material.RED_BED).setName(UI.color("Go back", UI.SECONDARY_COLOR, ChatColor.BOLD));
	public static final MenuItemBuilder CLOSE_BUTTON = new MenuItemBuilder(Material.OAK_DOOR).setName(UI.color("Close", ChatColor.RED, ChatColor.BOLD));
	public static final MenuItemBuilder CANCEL_BUTTON = new MenuItemBuilder(Material.GRAY_DYE).setName(UI.color("Cancel", ChatColor.GRAY, ChatColor.BOLD));
	public static final MenuItemBuilder SAVE_BUTTON = new MenuItemBuilder(Material.LIME_DYE).setName(UI.color("Save", ChatColor.GREEN, ChatColor.BOLD));
	public static final MenuItemBuilder BACKGROUND_BUTTON = new MenuItemBuilder(Material.GRAY_STAINED_GLASS_PANE);
	
	protected String title;
	protected MenuSize size;
	protected Inventory inventory;
	protected Map<Integer, MenuAction<Player, Menu, MenuItemBuilder, ClickType>> clickableItems = new HashMap<>();

	public Menu(String title) {
		this(title, MenuSize.MD);
	}
	
	public Menu(String title, MenuSize size) {
		this.title = ChatColor.translateAlternateColorCodes('&', title);
		this.size = size;
		this.inventory = Bukkit.createInventory(null, size.getSlots(), this.title);
	}
	
	public Menu(String title, InventoryType type) {
		this.title = ChatColor.translateAlternateColorCodes('&', title);
		this.inventory = Bukkit.createInventory(null, type, this.title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public MenuSize getSize() {
		return size;
	}
	
	public boolean isEmpty(int row, int column) {
		return isEmpty((row * 9) + column);
	}
	
	public boolean isEmpty(int slot) {
		return inventory.getItem(slot) == null;
	}
	
	public Menu setButton(MenuItemBuilder item, int slot) {
		MenuAction<Player, Menu, MenuItemBuilder, ClickType> listener = item.getClickListener();
		
		inventory.setItem(slot, item.toItemStack());
		
		if(listener != null) {
			clickableItems.put(slot, listener);
		}
		
		return this;
	}
	
	public void clear() {
		inventory.clear();
		clickableItems.clear();
	}
	
	public Menu removeButton(int slot) {
		ItemStack item = this.inventory.getItem(slot);
		
		if(item != null) {
			inventory.remove(item);
			clickableItems.remove(slot);
		}
		
		return this;
	}
	
	void handleClick(InventoryClickEvent event) {
		MenuAction<Player, Menu, MenuItemBuilder, ClickType> click = clickableItems.get(event.getSlot());
		MenuItemBuilder item = new MenuItemBuilder(event.getCurrentItem()).setClickListener(click);
		
		if (click != null) {
			click.onClick((Player) event.getWhoClicked(), this, item, event.getClick());
		}
	}
	
	Inventory getInventory() {
		return inventory;
	}
	
}
