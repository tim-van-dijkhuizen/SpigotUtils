package nl.timvandijkhuizen.spigotutils.menu.actions;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemAction;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;
import nl.timvandijkhuizen.spigotutils.menu.types.FileExplorereMenu;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ActionFileExplorer implements MenuItemAction {

	private File root;
	private Pattern[] allowed;
	private File selected;
	private Consumer<File> callback;
	
	public ActionFileExplorer(File root, File selected, Consumer<File> callback) {
		this(root, selected, null, callback);
	}
	
	public ActionFileExplorer(File root, File selected, Pattern[] allowed, Consumer<File> callback) {
		this.root = root;
		this.allowed = allowed;
		this.selected = selected;
		this.callback = callback;
	}
	
	@Override
	public void onClick(MenuItemClick event) {
		FileExplorereMenu menu = new FileExplorereMenu(root, selected, allowed, callback);
        Player whoClicked = event.getPlayer();
        Menu activeMenu = event.getMenu();
        MenuItemBuilder clickedItem = event.getItem();
        
        // Get current lore
        List<String> oldLore = clickedItem.getLore();
        Supplier<List<String>> oldLoreGenerator = clickedItem.getLoreGenerator();

        clickedItem.setLore(UI.color("Loading...", UI.COLOR_TEXT));
        clickedItem.setLoreGenerator(null);
        activeMenu.disableButtons();
        activeMenu.refresh();

        // Create menu
        menu.loadDirectory(root, () -> {
        	clickedItem.setLore(oldLore);
        	clickedItem.setLoreGenerator(oldLoreGenerator);
            activeMenu.enableButtons();
            
            menu.open(whoClicked);
        });
	}

}
