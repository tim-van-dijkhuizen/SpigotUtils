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

public class PagedMenu extends Menu {

    private int rows;
    private int columns;
    private int rowOffset;
    private int columnOffset;

    private MenuItemBuilder previousItem;
    private MenuItemBuilder nextItem;
    private MenuItemBuilder currentItem;

    private List<MenuItemBuilder> pagedItems = new ArrayList<>();
    private int page;

    public PagedMenu(String title, int rows, int columns, int rowOffset, int columnOffset, int previousItemOffset, int currentItemOffset, int nextItemOffset) {
        super(title, findMenuSize(rows, columns, rowOffset, columnOffset));

        // Set values
        this.rows = rows;
        this.columns = columns;
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;

        // Validate values
        if ((rowOffset + rows + rowOffset + 1) > 6 || (columnOffset + columns) > 9) {
            throw new RuntimeException("Invalid menu sizes");
        }

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

            if (((page + 1) * (rows * columns)) < getPagedItems().size()) {
                UI.playSound(whoClicked, UI.SOUND_CLICK);
                page += 1;
                refresh();
            }
        });

        setItem(nextItem, size.getSlots() - 9 + nextItemOffset);
    }

    public PagedMenu(String title, int rows, int columns, int rowOffset, int columnOffset) {
        this(title, rows, columns, rowOffset, columnOffset, 1, 4, 7);
    }

    public PagedMenu(String title, int rows, int columns) {
        this(title, rows, columns, 0, 0);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<MenuItemBuilder> getPagedItems() {
        return pagedItems;
    }

    public PagedMenu addPagedItem(MenuItemBuilder item) {
        pagedItems.add(item);
        return this;
    }

    public PagedMenu removePagedItem(MenuItemBuilder item) {
        pagedItems.remove(item);
        return this;
    }

    public void setPage(int page) {
        this.page = parsedPage(page);
    }

    public void open(Player player, int page) {
        setPage(page);
        open(player);
    }

    @Override
    protected void draw() {
        page = parsedPage(page);

        // Add paged items
        int rowStart = rowOffset;
        int columnStart = columnOffset;
        int itemIndex = page * (rows * columns);
        List<MenuItemBuilder> pagedItems = getPagedItems();

        for (int row = rowStart; row < (rowStart + rows); row++) {
            for (int column = columnStart; column < (columnStart + columns); column++) {
                int slot = row * 9 + column;

                // Clear slot
                removeItem(slot);

                // Set paged item if we've got one
                if (itemIndex < pagedItems.size()) {
                    setItem(pagedItems.get(itemIndex++), slot);
                }
            }
        }

        // Update current item
        currentItem.setName(UI.color("Page " + (page + 1) + "/" + getPageCount(), UI.COLOR_SECONDARY, ChatColor.BOLD));

        // Draw menu
        super.draw();
    }

    /**
     * Find the best fitting menu size depending on the amount of rows and
     * columns.
     * 
     * @param rows
     * @param columns
     * @param rowOffset
     * @param columnOffset
     * @return
     */
    private static MenuSize findMenuSize(int rows, int columns, int rowOffset, int columnOffset) {
        int max = (rowOffset + rows + rowOffset + 1) * 9;

        for (MenuSize size : MenuSize.values()) {
            if (size.getSlots() >= max)
                return size;
        }

        return MenuSize.XXL;
    }

    private int getPageCount() {
        return 1 + (int) (getPagedItems().size() / Double.valueOf(rows * columns));
    }

    private int parsedPage(int page) {
        if (page < 0) {
            return 0;
        }

        // Check if its over the max
        int max = getPageCount() - 1;

        if (page > max) {
            return max;
        }

        return page;
    }

}
