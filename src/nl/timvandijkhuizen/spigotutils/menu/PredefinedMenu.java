package nl.timvandijkhuizen.spigotutils.menu;

import org.bukkit.entity.Player;

public interface PredefinedMenu {

    Menu create(Player player, MenuArguments args);

}
