package nl.timvandijkhuizen.spigotutils.menu.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuSize;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class StructuredMenu extends Menu {

    private int[] itemSlots;

    private MenuItemBuilder previousItem;
    private MenuItemBuilder nextItem;
    private MenuItemBuilder currentItem;

    private List<MenuItemBuilder> structuredItems = new ArrayList<>();
    private int page;

    public StructuredMenu(String title, MenuSize size, int[] itemSlots, int previousItemOffset, int currentItemOffset, int nextItemOffset) {
        super(title, size);

        this.itemSlots = itemSlots;

        if (previousItemOffset < 0 || previousItemOffset > 8) {
            throw new RuntimeException("Previous item outside range");
        }

        if (nextItemOffset < 0 || nextItemOffset > 8) {
            throw new RuntimeException("Next item outside range");
        }

        // Fill bottom row with background items
        for (int i = 0; i < 9; i++) {
            setItem(MenuItems.BACKGROUND, size.getSlots() - 9 + i);
        }

        // Add previous item
        previousItem = MenuItems.PREVIOUS.clone().setClickListener(event -> {
            Player whoClicked = event.getPlayer();

            if (page > 0) {
                UI.playSound(whoClicked, UI.SOUND_CLICK);
                page -= 1;
                refresh();
            }
        });

        setItem(previousItem, size.getSlots() - 9 + previousItemOffset);

        // Add current item
        setItem(currentItem = MenuItems.CURRENT.clone(), size.getSlots() - 9 + currentItemOffset);

        // Add next item
        nextItem = MenuItems.NEXT.clone().setClickListener(event -> {
            Player whoClicked = event.getPlayer();

            if (((page + 1) * itemSlots.length) < structuredItems.size()) {
                UI.playSound(whoClicked, UI.SOUND_CLICK);
                page += 1;
                refresh();
            }
        });

        setItem(nextItem, size.getSlots() - 9 + nextItemOffset);
    }

    public StructuredMenu(String title, MenuSize size, int[] itemSlots) {
        this(title, size, itemSlots, 1, 4, 7);
    }

    public int[] getItemSlots() {
        return itemSlots;
    }

    public StructuredMenu addStructuredItem(MenuItemBuilder item) {
        structuredItems.add(item);
        return this;
    }

    public StructuredMenu removeStructuredItem(MenuItemBuilder item) {
        structuredItems.remove(item);
        return this;
    }

    public void open(Player player, int page) {
        this.page = page;
        open(player);
    }

    @Override
    protected void draw() {
        int itemIndex = page * itemSlots.length;

        for (int itemSlot : itemSlots) {
            removeItem(itemSlot);

            // Set structured item if we've got one
            if (itemIndex < structuredItems.size()) {
                setItem(structuredItems.get(itemIndex++), itemSlot);
            }
        }

        // Update current item
        int max = 1 + (int) (structuredItems.size() / Double.valueOf(itemSlots.length));

        currentItem.setName(UI.color("Page " + (page + 1) + "/" + max, UI.COLOR_SECONDARY, ChatColor.BOLD));

        // Draw menu
        super.draw();
    }

}
