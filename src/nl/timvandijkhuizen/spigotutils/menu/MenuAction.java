package nl.timvandijkhuizen.spigotutils.menu;

public interface MenuAction {

    /**
     * Called when the menu item is clicked.
     * 
     * @param event
     */
    void onClick(MenuItemClickEvent event);

}
