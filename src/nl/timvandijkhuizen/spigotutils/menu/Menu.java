package nl.timvandijkhuizen.spigotutils.menu;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;

public class Menu implements InventoryHolder {

    protected String title;
    protected MenuSize size;
    protected Inventory inventory;
    protected boolean disableItems;
    
    protected Map<Integer, MenuItemBuilder> items = new HashMap<>();
    protected Set<MenuClickListener> clickListeners = new LinkedHashSet<>();
    protected Set<Runnable> closeListeners = new LinkedHashSet<>();

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
     * Returns the item at the specified slot.
     * 
     * @param slot
     * @return
     */
    public MenuItemBuilder getItem(int slot) {
        return items.get(slot);
    }

    /**
     * Sets an item at the specified slot.
     * 
     * @param item
     * @param slot
     * @return
     */
    public Menu setItem(MenuItemBuilder item, int slot) {
        items.put(slot, item);
        return this;
    }

    /**
     * Removes the item at the specified slot.
     * 
     * @param slot
     * @return
     */
    public Menu removeItem(int slot) {
        items.remove(slot);
        return this;
    }

    /**
     * Clears the inventory items.
     */
    public void clear() {
        items.clear();
    }
    
    /**
     * Disable all items in this menu.
     * 
     * @return
     */
    public Menu disableItems() {
        disableItems = true;
        return this;
    }

    /**
     * Enable all items in this menu. If the item is
     * disabled you still won't be able to click it.
     * 
     * @return
     */
    public Menu enableItems() {
        disableItems = false;
        return this;
    }
    
    /**
     * Add a menu wide click listener.
     * 
     * @param listener
     */
    public void addClickListener(MenuClickListener listener) {
        clickListeners.add(listener);
    }
    
    /**
     * Remove a menu wide click listener.
     * 
     * @param listener
     */
    public void removeClickListener(MenuClickListener listener) {
        clickListeners.remove(listener);
    }
    
    /**
     * Add a menu close listener.
     * 
     * @param listener
     */
    public void addCloseListener(Runnable listener) {
        closeListeners.add(listener);
    }
    
    /**
     * Remove a menu close listener.
     * 
     * @param listener
     */
    public void removeCloseListener(Runnable listener) {
        closeListeners.remove(listener);
    }

    /**
     * Re-draw the menu.
     */
    public void refresh() {
        draw();

        for (HumanEntity viewer : inventory.getViewers()) {
            if (viewer instanceof Player) {
                ((Player) viewer).updateInventory();
            }
        }
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
        
        // Call close listeners
        for(Runnable listener : closeListeners) {
            listener.run();
        }
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

        // Draw menu items
        for (Entry<Integer, MenuItemBuilder> item : items.entrySet()) {
            inventory.setItem(item.getKey(), item.getValue().build());
        }
    }

    /**
     * Handles a click while the menu is active.
     * 
     * @param event
     * @return boolean
     */
    void handleClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        
        // Create click
        MenuItemBuilder item = items.get(slot);
        MenuClick click = new MenuClick(event, this, item);
        
        // Deny by default
        click.setCancelled(true);
        
        // Handle menu click
        for(MenuClickListener listener : clickListeners) {
            listener.onClick(click);
        }
        
        // Handle item click
        if (item != null && !disableItems) {
            MenuClickListener listener = item.getClickListener();

            if (listener != null && !item.isDisabled()) {
                listener.onClick(click);
            }
        }
    }

}
