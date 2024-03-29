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
import nl.timvandijkhuizen.spigotutils.data.TypedValue;
import nl.timvandijkhuizen.spigotutils.helpers.ConsoleHelper;
import nl.timvandijkhuizen.spigotutils.helpers.InventoryHelper;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
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
        this(clazz, menuTitle, InventoryHelper.parseMaterial(menuIcon));
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

                object = clazz.getConstructor().newInstance();
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
    public void getValueInput(OptionConfig config, ConfigOption<List<T>> option, MenuClick event, Consumer<List<T>> callback) {
        PagedMenu menu = new PagedMenu(menuTitle, 3, 7, 1, 1);
        List<T> objects = getValue(config, option);
        Player player = event.getPlayer();

        // Add command buttons
        for (T object : objects) {
            addObjectButton(player, menu, objects, object, option.isRequired());
        }

        // Back button
        // ===========================
        MenuItemBuilder backButton = MenuItems.BACK.clone();

        backButton.setClickListener(backClick -> {
            UI.playSound(player, UI.SOUND_CLICK);
            callback.accept(objects);
        });

        menu.setItem(backButton, menu.getSize().getSlots() - 9 + 3);

        // Create button
        // ===========================
        MenuItemBuilder createButton = new MenuItemBuilder(XMaterial.NETHER_STAR);

        createButton.setName(UI.color("Create", UI.COLOR_SECONDARY, ChatColor.BOLD));

        createButton.setClickListener(createClick -> {
            try {
                T object = createObject(null);

                UI.playSound(player, UI.SOUND_CLICK);
                player.closeInventory();

                object.getInput(createClick, save -> {
                    if(save) {
                        addObjectButton(player, menu, objects, object, option.isRequired());
                        objects.add(object);
                    }

                    menu.open(player);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        menu.setItem(createButton, menu.getSize().getSlots() - 9 + 5);

        // Open menu
        menu.open(player);
    }

    private void addObjectButton(Player player, PagedMenu menu, List<T> objects, T object, boolean required) {
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
            
            if(!required || objects.size() > 1) {
                lore.add(UI.color("Use right-click to delete.", UI.COLOR_SECONDARY, ChatColor.ITALIC));
            }
            
            return lore;
        });

        item.setClickListener(event -> {
            ClickType clickType = event.getClickType();

            if (clickType == ClickType.RIGHT) {
                if(!required || objects.size() > 1) {
                    UI.playSound(player, UI.SOUND_DELETE);
    
                    objects.remove(object);
                    menu.removePagedItem(item);
                    
                    menu.refresh();
                }
            } else {
                UI.playSound(player, UI.SOUND_CLICK);
                player.closeInventory();

                // Create clone
                TypedValue<T> clone = new TypedValue<>();
                
                try {
                    clone.set(createObject(object));
                } catch(Exception e) {
                    ConsoleHelper.printError("Failed to create object clone", e);
                    return;
                }
                
                clone.get().getInput(event, save -> {
                    if(save) {
                        copyObjectData(clone.get(), object);
                    }
                    
                    menu.open(player);
                });
            }
        });

        menu.addPagedItem(item);
    }
    
    private T createObject(T base) throws Exception {
        T object = clazz.getConstructor().newInstance();
        
        if(base != null) {
            copyObjectData(base, object);
        }
        
        return object;
    }
    
    private void copyObjectData(T source, T target) {
        ConfigObjectData data = new ConfigObjectData();
        
        try {
            source.serialize(data);
            target.deserialize(data);
        } catch(Throwable e) {
            ConsoleHelper.printError("Failed to copy config object data", e);
        }
    }

}
