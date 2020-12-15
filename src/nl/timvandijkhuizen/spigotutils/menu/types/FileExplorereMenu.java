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
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.io.Files;

import nl.timvandijkhuizen.spigotutils.helpers.ThreadHelper;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class FileExplorereMenu extends PagedMenu {

    private static final XMaterial DIRECTORY_ICON = XMaterial.CHEST;
    private static final XMaterial DEFAULT_ICON = XMaterial.PAPER;
    public static final Map<String, XMaterial> FILE_ICONS = new HashMap<>();

    static {
        FILE_ICONS.put("mp3", XMaterial.JUKEBOX);

        FILE_ICONS.put("mp4", XMaterial.ITEM_FRAME);

        FILE_ICONS.put("jpg", XMaterial.PAINTING);
        FILE_ICONS.put("jpeg", XMaterial.PAINTING);
        FILE_ICONS.put("png", XMaterial.PAINTING);
        FILE_ICONS.put("svg", XMaterial.PAINTING);
        FILE_ICONS.put("gif", XMaterial.PAINTING);
        FILE_ICONS.put("ico", XMaterial.PAINTING);

        FILE_ICONS.put("zip", XMaterial.ENDER_CHEST);
        FILE_ICONS.put("rar", XMaterial.ENDER_CHEST);

        FILE_ICONS.put("dat", XMaterial.BOOKSHELF);
        FILE_ICONS.put("db", XMaterial.BOOKSHELF);
        FILE_ICONS.put("sql", XMaterial.BOOKSHELF);

        FILE_ICONS.put("log", XMaterial.OAK_LOG);

        FILE_ICONS.put("bat", XMaterial.OBSERVER);
        FILE_ICONS.put("exe", XMaterial.OBSERVER);
        FILE_ICONS.put("jar", XMaterial.OBSERVER);

        FILE_ICONS.put("doc", XMaterial.BOOK);
        FILE_ICONS.put("docx", XMaterial.BOOK);
        FILE_ICONS.put("pdf", XMaterial.BOOK);
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

        // Create back item
        MenuItemBuilder backItem = MenuItems.BACK.clone();

        backItem.setName(UI.color("Go back", UI.COLOR_SECONDARY, ChatColor.BOLD));

        backItem.setClickListener(event -> {
            Player player = event.getPlayer();

            if (currentDirectory != null && !currentDirectory.getPath().equals(root.getPath())) {
                UI.playSound(player, UI.SOUND_CLICK);
                setCurrentDirectory(currentDirectory.getParentFile(), event);
            } else if (returnMenu != null) {
                UI.playSound(player, UI.SOUND_CLICK);
                returnMenu.open(player);
            } else {
                UI.playSound(player, UI.SOUND_ERROR);
            }
        });

        setItem(backItem, 48);

        // Create current directory item
        MenuItemBuilder currentItem = new MenuItemBuilder(FileExplorereMenu.DIRECTORY_ICON);

        currentItem.setName(UI.color("Current Directory", UI.COLOR_SECONDARY, ChatColor.BOLD));

        currentItem.setLoreGenerator(() -> {
            return Arrays.asList(UI.color(currentDirectory != null ? currentDirectory.getPath() : "", UI.COLOR_TEXT));
        });

        setItem(currentItem, 50);
    }

    @Override
    public List<MenuItemBuilder> getPagedItems() {
        if (currentFiles == null) {
            return new ArrayList<>();
        }

        return currentFiles;
    }

    public void setCurrentDirectory(File directory, MenuClick event) {
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
            currentFiles = createPagedItems(files);
            callback.run();
        });
    }

    private List<MenuItemBuilder> createPagedItems(File[] newFiles) {
        List<MenuItemBuilder> items = new ArrayList<>();

        // Get directories and files
        List<File> all = Arrays.asList(newFiles);
        List<File> directories = all.stream().filter(i -> i.isDirectory()).sorted().collect(Collectors.toList());
        List<File> files = all.stream().filter(i -> !i.isDirectory()).sorted().collect(Collectors.toList());

        // Add directories
        for (File directory : directories) {
            MenuItemBuilder item = new MenuItemBuilder(DIRECTORY_ICON);

            item.setName(UI.color(directory.getName(), UI.COLOR_PRIMARY, ChatColor.BOLD));

            item.setClickListener(event -> {
                UI.playSound(event.getPlayer(), UI.SOUND_CLICK);
                setCurrentDirectory(directory, event);
            });

            items.add(item);
        }

        // Add files
        for (File file : files) {
            String fileName = file.getName();
            String extension = Files.getFileExtension(fileName);

            // Create menu item
            XMaterial type = FILE_ICONS.getOrDefault(extension, DEFAULT_ICON);
            ChatColor color = isAllowed(fileName) ? UI.COLOR_PRIMARY : ChatColor.GRAY;
            MenuItemBuilder item = new MenuItemBuilder(type);

            item.setName(UI.color(fileName, color, ChatColor.BOLD));
            item.setGlowGenerator(() -> selected != null && selected.getPath().equals(file.getPath()));

            item.setClickListener(event -> {
                Player player = event.getPlayer();

                if (isAllowed(fileName)) {
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
        if (allowed == null) {
            return true;
        }

        for (Pattern pattern : allowed) {
            Matcher matcher = pattern.matcher(fileName);

            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }

}
