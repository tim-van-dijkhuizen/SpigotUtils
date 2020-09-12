package nl.timvandijkhuizen.spigotutils.menu.items;

public interface MenuAction {

    /**
     * Called when the menu item is clicked.
     * 
     * @param event
     */
    void onClick(MenuItemClick event);

}
