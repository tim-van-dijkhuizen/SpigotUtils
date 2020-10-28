package nl.timvandijkhuizen.spigotutils.config;

import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;

public interface ConfigObject {

    void serialize(ConfigObjectData output) throws Throwable;

    void deserialize(ConfigObjectData input) throws Throwable;

    String getItemName();

    String[] getItemLore();

    void getInput(MenuItemClick event, Runnable callback);

}
