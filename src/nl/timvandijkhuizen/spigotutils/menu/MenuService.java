package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import nl.timvandijkhuizen.spigotutils.services.Service;

public class MenuService implements Service, Listener {

    @Override
    public String getHandle() {
        return "menus";
    }

    @Override
    public void load() throws Exception {

    }

    @Override
    public void unload() throws Exception {

    }

    @EventHandler
    public void onClickMenu(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder != null && holder instanceof Menu) {
            Menu menu = (Menu) holder;
            
            event.setCancelled(true);
            menu.handleClick(event);
        }
    }

}
