package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;

public class MenuItemClickEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Menu menu;
    private MenuItemBuilder item;
    private ClickType clickType;

    public MenuItemClickEvent(Player player, Menu menu, MenuItemBuilder item, ClickType clickType) {
        this.player = player;
        this.menu = menu;
        this.item = item;
        this.clickType = clickType;
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

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}