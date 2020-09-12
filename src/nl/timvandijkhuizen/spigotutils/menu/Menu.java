package nl.timvandijkhuizen.spigotutils.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import nl.timvandijkhuizen.spigotutils.menu.items.MenuAction;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;

public class Menu implements InventoryHolder {

    protected String title;
    protected MenuSize size;
    protected Inventory inventory;
    protected Map<Integer, MenuItemBuilder> items = new HashMap<>();
    protected boolean disableButtons;

    public Menu(String title) {
        this(title, MenuSize.MD);
    }

    public Menu(String title, MenuSize size) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = size;
        this.inventory = Bukkit.createInventory(this, size.getSlots(), this.title);
    }

    public Menu(String title, InventoryType type) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.inventory = Bukkit.createInventory(this, type, this.title);
    }

    /**
     * Returns the menu title.
     * 
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the menu size.
     * 
     * @return
     */
    public MenuSize getSize() {
        return size;
    }

    /**
     * Returns whether the specified slot is empty.
     * 
     * @param slot
     * @return
     */
    public boolean isEmpty(int slot) {
        return !items.containsKey(slot);
    }

    /**
     * Returns the button at the specified slot.
     * 
     * @param slot
     * @return
     */
    public MenuItemBuilder getButton(int slot) {
        return items.get(slot);
    }
    
    /**
     * Sets an item at the specified slot.
     * 
     * @param item
     * @param slot
     * @return
     */
    public Menu setButton(MenuItemBuilder item, int slot) {
        items.put(slot, item);
        return this;
    }

    /**
     * Clears the inventory items.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Removes the item at the specified slot.
     * 
     * @param slot
     * @return
     */
    public Menu removeButton(int slot) {
        items.remove(slot);
        return this;
    }
    
    public Menu disableButtons() {
        disableButtons = true;
        return this;
    }
    
    public Menu enableButtons() {
        disableButtons = false;
        return this;
    }
    
    /**
     * Re-draw the menu.
     */
    public void refresh() {
        draw();
    }
    
    /**
     * Opens the menu.
     * 
     * @param player
     */
    public void open(Player player) {
        this.draw();
        player.openInventory(inventory);
    }
    
    /**
     * Close the menu.
     * 
     * @param player
     */
    public void close(Player player) {
        player.closeInventory();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * Clears the inventory and adds the items.
     */
    protected void draw() {
        inventory.clear();
        
        for(Entry<Integer, MenuItemBuilder> item : items.entrySet()) {
            inventory.setItem(item.getKey(), item.getValue().toItemStack());
        }
    }
    
    /**
     * Handles a click while the menu is active.
     * 
     * @param event
     */
    void handleClick(InventoryClickEvent event) {
        MenuItemBuilder item = items.get(event.getSlot());
        Player player = (Player) event.getWhoClicked();

        // Ignore if empty or disabled
        if (item == null || disableButtons) {
            return;
        }

        // Check if item has a click listener
        MenuAction listener = item.getClickListener();

        if (listener != null && !item.isDisabled()) {
            listener.onClick(new MenuItemClick(player, this, item, event.getClick()));
        }
    }

}
