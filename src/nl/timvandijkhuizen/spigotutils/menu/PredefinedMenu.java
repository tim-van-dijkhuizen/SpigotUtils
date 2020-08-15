package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.data.DataValue;

public interface PredefinedMenu {

    Menu create(Player player, DataValue... args);

}
