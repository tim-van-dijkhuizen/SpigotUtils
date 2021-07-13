package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import nl.timvandijkhuizen.spigotutils.services.Service;

public class MenuService implements Service, Listener {
    
    @EventHandler
    public void onClickMenu(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        // Handle click
        if(inventory != null) {
            InventoryHolder holder = inventory.getHolder();

            if (holder != null && holder instanceof Menu) {
                ((Menu) holder).handleClick(event);
            }
        }
    }

}
