package nl.timvandijkhuizen.spigotutils.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.timvandijkhuizen.spigotutils.services.Service;

public class MenuService implements Service, Listener {

	private Map<UUID, Menu> menus = new HashMap<>();
	
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
		Player player = (Player) event.getWhoClicked();
		Menu menu = getMenu(player.getUniqueId());
		
		if (menu != null) {
			event.setCancelled(true);
			menu.handleClick(event);
		}
	}
	
	@EventHandler
	public void onCloseMenu(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Menu menu = getMenu(player.getUniqueId());
		
		if (menu != null) {
			menus.remove(player.getUniqueId());
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Menu menu = getMenu(player.getUniqueId());
		
		// Handle quit
		if (menu != null) {
			menus.remove(player.getUniqueId());
		}
	}
	
	public void openMenu(Player player, Menu menu) {
		player.openInventory(menu.getInventory());
		menus.put(player.getUniqueId(), menu);
	}
	
	public void openMenu(Player player, PagedMenu menu) {
		menu.setPage(0);
		player.openInventory(menu.getInventory());
		menus.put(player.getUniqueId(), menu);
	}
	
	public Menu getMenu(UUID uuid) {
		return menus.get(uuid);
	}
	
}
