package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import nl.timvandijkhuizen.spigotutils.services.BaseService;

public class MenuService extends BaseService implements Listener {

    @Override
    public String getHandle() {
        return "menus";
    }

    @EventHandler
    public void onClickMenu(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        InventoryHolder holder = inventory.getHolder();

        if (holder != null && holder instanceof Menu) {
            Menu menu = (Menu) holder;
            boolean cancelled = menu.handleClick(event);

            event.setCancelled(cancelled);
        }
    }

}
