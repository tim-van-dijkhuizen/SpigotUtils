package nl.timvandijkhuizen.spigotutils.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PagedMenu extends Menu {

    public static final MenuItemBuilder BACK_BUTTON = new MenuItemBuilder(Material.ARROW).setName("&b&lPrevious page");
    public static final MenuItemBuilder NEXT_BUTTON = new MenuItemBuilder(Material.ARROW).setName("&b&lNext page");
    public static final MenuItemBuilder CLOSE_BUTTON = new MenuItemBuilder(Material.BARRIER).setName("&c&lClose");

    private int rows;
    private int columns;
    private int rowOffset;
    private int columnOffset;

    private int previousButtonOffset;
    private int nextButtonOffset;

    private List<MenuItemBuilder> pagedItems = new ArrayList<>();
    private Map<Integer, MenuItemBuilder> extraItems = new HashMap<>();
    private int page;

    public PagedMenu(String title, int rows, int columns, int rowOffset, int columnOffset, int previousButtonOffset, int nextButtonOffset) {
        super(title, findMenuSize(rows, columns, rowOffset, columnOffset));

        this.rows = rows;
        this.columns = columns;
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
        this.previousButtonOffset = previousButtonOffset;
        this.nextButtonOffset = nextButtonOffset;

        if ((rowOffset + rows + rowOffset + 1) > 6 || (columnOffset + columns) > 9) {
            throw new RuntimeException("Invalid menu sizes");
        }

        if (previousButtonOffset < 0 || previousButtonOffset > 8) {
            throw new RuntimeException("Previous button outside range");
        }

        if (nextButtonOffset < 0 || nextButtonOffset > 8) {
            throw new RuntimeException("Next button outside range");
        }
    }

    public PagedMenu(String title, int rows, int columns, int rowOffset, int columnOffset) {
        this(title, rows, columns, rowOffset, columnOffset, 2, 6);
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

    @Override
    public Menu setButton(MenuItemBuilder item, int slot) {
        extraItems.put(slot, item);
        return this;
    }

    @Override
    public Menu removeButton(int slot) {
        extraItems.remove(slot);
        return this;
    }

    public PagedMenu addPagedButton(MenuItemBuilder item) {
        pagedItems.add(item);
        return this;
    }

    public PagedMenu removePagedButton(MenuItemBuilder item) {
        pagedItems.remove(item);
        return this;
    }

    public void refresh() {
        setPage(page);
    }

    void setPage(int page) {
        int rowStart = rowOffset;
        int columnStart = columnOffset;
        int itemIndex = page * (rows * columns);

        this.page = page;
        this.clear();

        for (int row = rowStart; row < (rowStart + rows); row++) {
            for (int column = columnStart; column < (columnStart + columns); column++) {
                if (itemIndex < pagedItems.size()) {
                    super.setButton(pagedItems.get(itemIndex++), row * 9 + column);
                }
            }
        }

        // Add extra items
        for (Entry<Integer, MenuItemBuilder> entry : extraItems.entrySet()) {
            super.setButton(entry.getValue(), entry.getKey());
        }

        // Add pagination
        super.setButton(BACK_BUTTON.setClickListener(event -> {
            Player whoClicked = event.getPlayer();

            whoClicked.playSound(whoClicked.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

            if (page > 0) {
                setPage(page - 1);
            }
        }), size.getSlots() - 9 + previousButtonOffset);

        super.setButton(NEXT_BUTTON.setClickListener(event -> {
            Player whoClicked = event.getPlayer();

            whoClicked.playSound(whoClicked.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

            if (((page + 1) * (rows * columns)) < pagedItems.size()) {
                setPage(page + 1);
            }
        }), size.getSlots() - 9 + nextButtonOffset);

        // Fill the gaps
        for (int i = 0; i < 9; i++) {
            if (!isEmpty(size.getSlots() - 9 + i)) {
                continue;
            }

            super.setButton(Menu.BACKGROUND_BUTTON, size.getSlots() - 9 + i);
        }
    }

    private static MenuSize findMenuSize(int rows, int columns, int rowOffset, int columnOffset) {
        int max = (rowOffset + rows + rowOffset + 1) * 9;

        for (MenuSize size : MenuSize.values()) {
            if (size.getSlots() >= max)
                return size;
        }

        return MenuSize.XXL;
    }

}
