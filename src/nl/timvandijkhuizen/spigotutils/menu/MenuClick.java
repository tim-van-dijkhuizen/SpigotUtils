package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;

public class MenuClick {

    private MenuItemBuilder item;
    private Player player;
    private Menu menu;
    private ClickType clickType;
    private boolean cancelled;
    
    public MenuClick(Player player, Menu menu, MenuItemBuilder item, ClickType clickType) {
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

    public ClickType getClickType() {
        return clickType;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public MenuItemBuilder getItem() {
        return item;
    }

}