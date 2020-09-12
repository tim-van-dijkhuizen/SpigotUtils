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

    private int[] buttonSlots;
    
    private MenuItemBuilder previousButton;
    private MenuItemBuilder nextButton;
    private MenuItemBuilder currentButton;

    private List<MenuItemBuilder> structuredItems = new ArrayList<>();
    private int page;

    public StructuredMenu(String title, MenuSize size, int[] buttonSlots, int previousButtonOffset, int currentButtonOffset, int nextButtonOffset) {
        super(title, size);

        this.buttonSlots = buttonSlots;

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
                UI.playSound(whoClicked, UI.SOUND_CLICK);
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

            if (((page + 1) * buttonSlots.length) < structuredItems.size()) {
                UI.playSound(whoClicked, UI.SOUND_CLICK);
                page += 1;
                refresh();
            }
        });
        
        setButton(nextButton, size.getSlots() - 9 + nextButtonOffset);
    }

    public StructuredMenu(String title, MenuSize size, int[] buttonSlots) {
        this(title, size, buttonSlots, 1, 4, 7);
    }

    public int[] getButtonSlots() {
        return buttonSlots;
    }

    public StructuredMenu addStructuredButton(MenuItemBuilder item) {
        structuredItems.add(item);
        return this;
    }

    public StructuredMenu removeStructuredButton(MenuItemBuilder item) {
        structuredItems.remove(item);
        return this;
    }
    
    public void open(Player player, int page) {
        this.page = page;
        open(player);
    }

    @Override
    protected void draw() {
        int itemIndex = page * buttonSlots.length;

        for (int buttonSlot : buttonSlots) {
            removeButton(buttonSlot);
            
            // Set structured item if we've got one
            if (itemIndex < structuredItems.size()) {
                setButton(structuredItems.get(itemIndex++), buttonSlot);
            }
        }
        
        // Update current button
        int max = 1 + (int) (structuredItems.size() / Double.valueOf(buttonSlots.length));
        
        currentButton.setName(UI.color("Page " + (page + 1) + "/" + max, UI.COLOR_SECONDARY, ChatColor.BOLD));
        
        // Draw menu
        super.draw();
    }

}
