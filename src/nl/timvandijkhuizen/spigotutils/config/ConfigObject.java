package nl.timvandijkhuizen.spigotutils.config;

import org.bukkit.entity.Player;

public interface ConfigObject {

    void serialize(ConfigObjectData output) throws Throwable;

    void deserialize(ConfigObjectData input) throws Throwable;

    String getItemName();

    String[] getItemLore();

    void getInput(Player player, Runnable callback);

}
