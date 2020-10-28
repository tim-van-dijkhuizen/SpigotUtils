package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.config.ConfigObject;
import nl.timvandijkhuizen.spigotutils.config.ConfigObjectData;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.menu.types.PagedMenu;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeList<T extends ConfigObject> implements ConfigType<List<T>> {

    private Class<T> clazz;
    private String menuTitle;
    private Material menuIcon;

    public ConfigTypeList(Class<T> clazz, String menuTitle, Material menuIcon) {
        this.clazz = clazz;
        this.menuTitle = menuTitle;
        this.menuIcon = menuIcon;
    }

    public ConfigTypeList(Class<T> clazz, String menuTitle, XMaterial menuIcon) {
        this(clazz, menuTitle, menuIcon.parseMaterial(true));
    }

    public ConfigTypeList(Class<T> clazz, String menuTitle) {
        this(clazz, menuTitle, XMaterial.PAPER);
    }

    @Override
    public List<T> getValue(OptionConfig config, ConfigOption<List<T>> option) {
        List<T> output = new ArrayList<>();

        for (Map<?, ?> rawObject : config.getMapList(option.getPath())) {
            T object;

            try {
                ConfigObjectData data = new ConfigObjectData(rawObject);

                object = clazz.newInstance();
                object.deserialize(data);
                
                output.add(object);
            } catch (Throwable e) {
                ConsoleHelper.printError("Failed to deserialize config object: " + rawObject.toString(), e);
            }
        }

        return output;
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<List<T>> option, List<T> value) {
        if (value != null) {
            List<Map<String, Object>> output = new ArrayList<>();

            for (T object : value) {
                ConfigObjectData data = new ConfigObjectData();

                try {
                    object.serialize(data);
                    output.add(data.toMap());
                } catch (Throwable e) {
                    ConsoleHelper.printError("Failed to serialize config object: " + data.toString(), e);
                }
            }

            config.set(option.getPath(), output);
        } else {
            config.set(option.getPath(), null);
        }
    }

    @Override
    public String getRawValue(OptionConfig config, ConfigOption<List<T>> option) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<List<T>> option) {
        String[] items = getValue(config, option).stream()
            .map(i -> i.getItemName())
            .toArray(String[]::new);

        return String.join(", ", items);
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<List<T>> option) {
        return !config.contains(option.getPath()) || getValue(config, option).isEmpty();
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<List<T>> option, MenuItemClick event, Consumer<List<T>> callback) {
        PagedMenu menu = new PagedMenu(menuTitle, 3, 7, 1, 1);
        List<T> objects = getValue(config, option);
        Player player = event.getPlayer();

        // Add command buttons
        for (T object : objects) {
            addObjectButton(player, menu, objects, object);
        }

        // Back button
        // ===========================
        MenuItemBuilder backButton = MenuItems.BACK.clone();

        backButton.setClickListener(backClick -> {
            UI.playSound(player, UI.SOUND_CLICK);
            callback.accept(objects);
        });

        menu.setButton(backButton, menu.getSize().getSlots() - 9 + 3);

        // Create button
        // ===========================
        MenuItemBuilder createButton = new MenuItemBuilder(XMaterial.NETHER_STAR);

        createButton.setName(UI.color("Create", UI.COLOR_SECONDARY, ChatColor.BOLD));

        createButton.setClickListener(createClick -> {
            try {
                T object = clazz.newInstance();

                UI.playSound(player, UI.SOUND_CLICK);
                player.closeInventory();

                object.getInput(createClick, () -> {
                    addObjectButton(player, menu, objects, object);
                    objects.add(object);

                    UI.playSound(player, UI.SOUND_CLICK);
                    menu.open(player);
                });
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        menu.setButton(createButton, menu.getSize().getSlots() - 9 + 5);

        // Open menu
        menu.open(player);
    }

    private void addObjectButton(Player player, PagedMenu menu, List<T> objects, T object) {
        MenuItemBuilder item = new MenuItemBuilder(menuIcon);

        item.setNameGenerator(() -> {
            return UI.color(object.getItemName(), UI.COLOR_PRIMARY, ChatColor.BOLD);
        });
        
        item.setLoreGenerator(() -> {
            List<String> lore = new ArrayList<>();
            
            for(String line : object.getItemLore()) {
                lore.add(line);
            }

            lore.add("");
            lore.add(UI.color("Use left-click to edit.", UI.COLOR_SECONDARY, ChatColor.ITALIC));
            lore.add(UI.color("Use right-click to delete.", UI.COLOR_SECONDARY, ChatColor.ITALIC));
            
            return lore;
        });

        item.setClickListener(event -> {
            ClickType clickType = event.getClickType();

            if (clickType == ClickType.RIGHT) {
                UI.playSound(player, UI.SOUND_DELETE);

                objects.remove(object);
                menu.removePagedButton(item);
                
                menu.refresh();
            } else {
                UI.playSound(player, UI.SOUND_CLICK);
                player.closeInventory();

                object.getInput(event, () -> {
                    menu.open(player);
                });
            }
        });

        menu.addPagedButton(item);
    }

}
