package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;

public class MenuClick {

    private InventoryClickEvent event;
    private Menu menu;
    private MenuItemBuilder item;
    
    public MenuClick(InventoryClickEvent event, Menu menu, MenuItemBuilder item) {
        this.event = event;
        this.menu = menu;
        this.item = item;
    }
    
    public Player getPlayer() {
        return (Player) event.getWhoClicked();
    }

    public int getSlot() {
        return event.getSlot();
    }
    
    public ClickType getClickType() {
        return event.getClick();
    }

    public ItemStack getCursor() {
        return event.getCursor();
    }
    
    public InventoryAction getAction() {
        return event.getAction();
    }
    
    public boolean isCancelled() {
        return event.isCancelled();
    }

    public void setCancelled(boolean cancel) {
        event.setCancelled(cancel);
    }

    public Menu getMenu() {
        return menu;
    }
    
    public MenuItemBuilder getItem() {
        return item;
    }

}