package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MenuItemClick {

    private Player player;
    private Menu menu;
    private MenuItemBuilder item;
    private ClickType clickType;

    public MenuItemClick(Player player, Menu menu, MenuItemBuilder item, ClickType clickType) {
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

}