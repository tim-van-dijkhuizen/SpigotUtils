package nl.timvandijkhuizen.spigotutils.menu.types;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.io.Files;

import nl.timvandijkhuizen.spigotutils.helpers.ThreadHelper;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class FileExplorereMenu extends PagedMenu {

	private static final Material DIRECTORY_ICON = Material.CHEST;
	private static final Material DEFAULT_ICON = Material.PAPER;
	public static final Map<String, Material> FILE_ICONS = new HashMap<>();
	
	static {
		FILE_ICONS.put("mp3", Material.JUKEBOX);
		
		FILE_ICONS.put("mp4", Material.ITEM_FRAME);
		
		FILE_ICONS.put("jpg", Material.PAINTING);
		FILE_ICONS.put("jpeg", Material.PAINTING);
		FILE_ICONS.put("png", Material.PAINTING);
		FILE_ICONS.put("svg", Material.PAINTING);
		FILE_ICONS.put("gif", Material.PAINTING);
		FILE_ICONS.put("ico", Material.PAINTING);
		
		FILE_ICONS.put("zip", Material.ENDER_CHEST);
		FILE_ICONS.put("rar", Material.ENDER_CHEST);
		
		FILE_ICONS.put("dat", Material.BOOKSHELF);
		FILE_ICONS.put("db", Material.BOOKSHELF);
		FILE_ICONS.put("sql", Material.BOOKSHELF);
		
		FILE_ICONS.put("log", Material.OAK_LOG);
		
		FILE_ICONS.put("bat", Material.OBSERVER);
		FILE_ICONS.put("exe", Material.OBSERVER);
		FILE_ICONS.put("jar", Material.OBSERVER);
		
		FILE_ICONS.put("doc", Material.BOOK);
		FILE_ICONS.put("docx", Material.BOOK);
		FILE_ICONS.put("pdf", Material.BOOK);
	}
	
	private File selected;
	private Pattern[] allowed;
	private Consumer<File> callback;
	
	private File currentDirectory;
	private List<MenuItemBuilder> currentFiles;
	
	public FileExplorereMenu(File root, File selected, Pattern[] allowed, Consumer<File> callback) {
		this(root, selected, allowed, callback, null);
	}
	
	public FileExplorereMenu(File root, File selected, Pattern[] allowed, Consumer<File> callback, Menu returnMenu) {
		super("File Explorer", 3, 7, 1, 1);
		this.selected = selected;
		this.allowed = allowed;
		this.callback = callback;
		
		// Create back button
		MenuItemBuilder backButton = MenuItems.BACK.clone();
		
		backButton.setName(UI.color("Go back", UI.COLOR_SECONDARY, ChatColor.BOLD));
		
		backButton.setClickListener(event -> {
			Player player = event.getPlayer();
			
			if(currentDirectory != null && !currentDirectory.getPath().equals(root.getPath())) {
				UI.playSound(player, UI.SOUND_CLICK);
				setCurrentDirectory(currentDirectory.getParentFile(), event);
			} else if(returnMenu != null) {
				UI.playSound(player, UI.SOUND_CLICK);
				returnMenu.open(player);
			} else {
				UI.playSound(player, UI.SOUND_ERROR);
			}
		});
		
		setButton(backButton, 48);
		
		// Create current directory button
		MenuItemBuilder currentButton = new MenuItemBuilder(FileExplorereMenu.DIRECTORY_ICON);
		
		currentButton.setName(UI.color("Current Directory", UI.COLOR_SECONDARY, ChatColor.BOLD));
		
		currentButton.setLoreGenerator(() -> {
			return Arrays.asList(UI.color(currentDirectory != null ? currentDirectory.getPath() : "", UI.COLOR_TEXT));
		});
		
		setButton(currentButton, 50);
	}
	
	@Override
	public List<MenuItemBuilder> getPagedButtons() {
		if(currentFiles == null) {
			return new ArrayList<>();
		}
		
		return currentFiles;
	}
	
	public void setCurrentDirectory(File directory, MenuItemClick event) {
		Menu menu = event.getMenu();
		MenuItemBuilder item = event.getItem();
		List<String> oldLore = item.getLore();
		
		item.setLore(UI.color("Loading...", UI.COLOR_TEXT));
		menu.refresh();
		
		loadDirectory(directory, () -> {
			item.setLore(oldLore);
			menu.refresh();
		});
	}
	
	public void loadDirectory(File directory, Runnable callback) {
		ThreadHelper.getAsync(() -> directory.listFiles(), files -> {
			currentDirectory = directory;
			currentFiles = createPagedButtons(files);
			callback.run();
		});
	}
	
	private List<MenuItemBuilder> createPagedButtons(File[] newFiles) {
		List<MenuItemBuilder> items = new ArrayList<>();
		
		// Get directories and files
		List<File> all = Arrays.asList(newFiles);
		List<File> directories = all.stream().filter(i -> i.isDirectory()).sorted().collect(Collectors.toList());
		List<File> files = all.stream().filter(i -> !i.isDirectory()).sorted().collect(Collectors.toList());
		
		// Add directories
		for(File directory : directories) {
			MenuItemBuilder item = new MenuItemBuilder(DIRECTORY_ICON);
			
			item.setName(UI.color(directory.getName(), UI.COLOR_PRIMARY, ChatColor.BOLD));

			item.setClickListener(event -> {
				UI.playSound(event.getPlayer(), UI.SOUND_CLICK);
				setCurrentDirectory(directory, event);
			});
			
			items.add(item);
		}
		
		// Add files
		for(File file : files) {
			String fileName = file.getName();
			String extension = Files.getFileExtension(fileName);
			
			// Create menu item
			Material type = FILE_ICONS.getOrDefault(extension, DEFAULT_ICON);
			ChatColor color = isAllowed(fileName) ? UI.COLOR_PRIMARY : ChatColor.GRAY;
			MenuItemBuilder item = new MenuItemBuilder(type);
			
			item.setName(UI.color(fileName, color, ChatColor.BOLD));
			item.setGlowGenerator(() -> selected != null && selected.getPath().equals(file.getPath()));
			
			item.setClickListener(event -> {
				Player player = event.getPlayer();
				
				if(isAllowed(fileName)) {
					UI.playSound(player, UI.SOUND_CLICK);
					callback.accept(selected = file);
				} else {
					UI.playSound(player, UI.SOUND_ERROR);
				}
			});
			
			items.add(item);
		}
		
		return items;
	}
	
	private boolean isAllowed(String fileName) {
		if(allowed == null) {
			return true;
		}
		
		for(Pattern pattern : allowed) {
			Matcher matcher = pattern.matcher(fileName);
			
			if(matcher.matches()) {
				return true;
			}
		}
		
		return false;
	}
	
}
