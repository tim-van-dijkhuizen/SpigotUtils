package nl.timvandijkhuizen.spigotutils.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu {

	protected String title;
	protected MenuSize size;
	protected Inventory inventory;
	protected Map<Integer, Consumer<Player>> clickableItems = new HashMap<>();

	public Menu(String title) {
		this(title, MenuSize.MD);
	}
	
	public Menu(String title, MenuSize size) {
		this.title = ChatColor.translateAlternateColorCodes('&', title);
		this.size = size;
		this.inventory = Bukkit.createInventory(null, size.getSize(), this.title);
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
	
	public Menu setButton(MenuItemBuilder item, int row, int column) {
		return setButton(item, (row * 9) + column);
	}
	
	public Menu setButton(MenuItemBuilder item, int slot) {
		Consumer<Player> listener = item.getClickListener();
		
		inventory.setItem(slot, item.toItemStack());
		inventory.getViewers().forEach(p -> ((Player) p).updateInventory());
		
		if(listener != null) {
			clickableItems.put(slot, listener);
		}
		
		return this;
	}
	
	public void clear() {
		inventory.clear();
		clickableItems.clear();
		inventory.getViewers().forEach(p -> ((Player) p).updateInventory());
	}
	
	public Menu removeButton(int row, int column) {
		return removeButton((row * 9) + column);
	}
	
	public Menu removeButton(int slot) {
		ItemStack item = this.inventory.getItem(slot);
		
		if(item != null) {
			inventory.remove(item);
			clickableItems.remove(slot);
		}
		
		return this;
	}
	
	void handleClick(int slot, Player player) {
		Consumer<Player> click = clickableItems.get(slot);
		
		if (click != null) {
			click.accept(player);
		}
	}
	
	Inventory getInventory() {
		return inventory;
	}
	
}
