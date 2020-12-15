package nl.timvandijkhuizen.spigotutils.menu.actions;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.menu.MenuClickListener;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.types.FileExplorereMenu;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ActionFileExplorer implements MenuClickListener {

    private File root;
    private Pattern[] allowed;
    private File selected;
    private Consumer<File> callback;
    private Menu returnMenu;

    public ActionFileExplorer(File root, File selected, Consumer<File> callback) {
        this(root, selected, null, callback, null);
    }

    public ActionFileExplorer(File root, File selected, Pattern[] allowed, Consumer<File> callback, Menu returnMenu) {
        this.root = root;
        this.allowed = allowed;
        this.selected = selected;
        this.callback = callback;
        this.returnMenu = returnMenu;
    }

    @Override
    public void onClick(MenuClick event) {
        FileExplorereMenu menu = new FileExplorereMenu(root, selected, allowed, callback, returnMenu);
        Player whoClicked = event.getPlayer();
        Menu activeMenu = event.getMenu();
        MenuItemBuilder clickedItem = event.getItem();

        // Get current lore
        List<String> oldLore = clickedItem.getLore();
        Supplier<List<String>> oldLoreGenerator = clickedItem.getLoreGenerator();

        clickedItem.setLore(UI.color("Loading...", UI.COLOR_TEXT));
        clickedItem.setLoreGenerator(null);
        activeMenu.disableItems();
        activeMenu.refresh();

        // Create menu
        menu.loadDirectory(root, () -> {
            clickedItem.setLore(oldLore);
            clickedItem.setLoreGenerator(oldLoreGenerator);
            activeMenu.enableItems();

            menu.open(whoClicked);
        });
    }

}
