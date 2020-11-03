package nl.timvandijkhuizen.spigotutils.menu.items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import nl.timvandijkhuizen.spigotutils.menu.Menu;

public class MenuItemClick {

    private Player player;
    private Menu menu;
    private MenuItemBuilder item;
    private ClickType clickType;
    private boolean cancelled;
    
    public MenuItemClick(Player player, Menu menu, MenuItemBuilder item, ClickType clickType) {
        this.player = player;
        this.menu = menu;
        this.item = item;
        this.clickType = clickType;
        this.cancelled = true;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuItemBuilder getItem() {
        return item;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}