package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

@FunctionalInterface
public interface MenuAction<One, Two, Three, Four> {

	void onClick(Player whoClicked, Menu activeMenu, MenuItemBuilder clickedItem, ClickType clickType);
	
}
