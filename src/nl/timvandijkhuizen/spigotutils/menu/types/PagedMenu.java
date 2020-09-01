package nl.timvandijkhuizen.spigotutils.menu.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.MenuItems;
import nl.timvandijkhuizen.spigotutils.menu.MenuSize;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class PagedMenu extends Menu {

    private int rows;
    private int columns;
    private int rowOffset;
    private int columnOffset;
    
    private MenuItemBuilder previousButton;
    private MenuItemBuilder nextButton;
    private MenuItemBuilder currentButton;

    private List<MenuItemBuilder> pagedItems = new ArrayList<>();
    private int page;

    public PagedMenu(String title, int rows, int columns, int rowOffset, int columnOffset, int previousButtonOffset, int currentButtonOffset, int nextButtonOffset) {
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

        if (previousButtonOffset < 0 || previousButtonOffset > 8) {
            throw new RuntimeException("Previous button outside range");
        }

        if (nextButtonOffset < 0 || nextButtonOffset > 8) {
            throw new RuntimeException("Next button outside range");
        }
        
        // Fill bottom row with background items
        for (int i = 0; i < 9; i++) {
            setButton(MenuItems.BACKGROUND, size.getSlots() - 9 + i);
        }
        
        // Add previous button
        previousButton = MenuItems.PREVIOUS.clone().setClickListener(event -> {
            Player whoClicked = event.getPlayer();

            if (page > 0) {
                UI.playSound(whoClicked, UI.CLICK_SOUND);
                page -= 1;
                refresh();
            }
        });
        
        setButton(previousButton, size.getSlots() - 9 + previousButtonOffset);

        // Add current button
        setButton(currentButton = MenuItems.CURRENT.clone(), size.getSlots() - 9 + currentButtonOffset);
        
        // Add next button
        nextButton = MenuItems.NEXT.clone().setClickListener(event -> {
            Player whoClicked = event.getPlayer();

            if (((page + 1) * (rows * columns)) < pagedItems.size()) {
                UI.playSound(whoClicked, UI.CLICK_SOUND);
                page += 1;
                refresh();
            }
        });
        
        setButton(nextButton, size.getSlots() - 9 + nextButtonOffset);
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

    public PagedMenu addPagedButton(MenuItemBuilder item) {
        pagedItems.add(item);
        return this;
    }

    public PagedMenu removePagedButton(MenuItemBuilder item) {
        pagedItems.remove(item);
        return this;
    }
    
    public void open(Player player, int page) {
        this.page = page;
        open(player);
    }

    @Override
    protected void draw() {
        int rowStart = rowOffset;
        int columnStart = columnOffset;
        int itemIndex = page * (rows * columns);

        for (int row = rowStart; row < (rowStart + rows); row++) {
            for (int column = columnStart; column < (columnStart + columns); column++) {
                int slot = row * 9 + column;
                
                // Clear slot
                removeButton(slot);
                
                // Set paged item if we've got one
                if (itemIndex < pagedItems.size()) {
                    setButton(pagedItems.get(itemIndex++), slot);
                }
            }
        }
        
        // Update current button
        int max = 1 + (int) (pagedItems.size() / Double.valueOf(rows * columns));
        
        currentButton.setName(UI.color("Page " + (page + 1) + "/" + max, UI.SECONDARY_COLOR, ChatColor.BOLD));
        
        // Draw menu
        super.draw();
    }

    /**
     * Find the best fitting menu size depending
     * on the amount of rows and columns.
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

}
