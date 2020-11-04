package nl.timvandijkhuizen.spigotutils.config;

import java.util.function.Consumer;

import nl.timvandijkhuizen.spigotutils.menu.MenuClick;

public interface ConfigObject {

    void serialize(ConfigObjectData output) throws Throwable;

    void deserialize(ConfigObjectData input) throws Throwable;

    String getItemName();

    String[] getItemLore();

    void getInput(MenuClick event, Consumer<Boolean> callback);

}
