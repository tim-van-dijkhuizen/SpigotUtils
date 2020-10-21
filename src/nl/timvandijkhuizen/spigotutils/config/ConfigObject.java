package nl.timvandijkhuizen.spigotutils.config;

import org.bukkit.entity.Player;

public interface ConfigObject {

    void serialize(ConfigObjectData output);

    void deserialize(ConfigObjectData input);

    String getItemName();

    String[] getItemLore();

    void getInput(Player player, Runnable callback);

}
