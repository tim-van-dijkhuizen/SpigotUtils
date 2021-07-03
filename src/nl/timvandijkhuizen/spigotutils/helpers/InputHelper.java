package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.SpigotUtils;
import nl.timvandijkhuizen.spigotutils.input.InputHandler;
import nl.timvandijkhuizen.spigotutils.input.InvalidInputException;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuSize;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class InputHelper {

    public static void getString(Player player, String question, InputHandler<String> callback) {
        ConversationFactory factory = new ConversationFactory(SpigotUtils.getInstance());

        Conversation conversation = factory.withFirstPrompt(new StringPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return question;
            }

            @Override
            public Prompt acceptInput(ConversationContext context, String input) {
                try {
                    return callback.handle(context, input);
                } catch(InvalidInputException e) {
                    context.getForWhom().sendRawMessage(UI.color(e.getMessage(), UI.COLOR_ERROR));
                    return this;
                }
            }
        }).withLocalEcho(false).buildConversation(player);

        conversation.begin();
    }
    
    public static void getNumber(Player player, String question, InputHandler<Number> callback) {
        ConversationFactory factory = new ConversationFactory(SpigotUtils.getInstance());

        Conversation conversation = factory.withFirstPrompt(new NumericPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return question;
            }

            @Override
            protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
                try {
                    return callback.handle(context, input);
                } catch(InvalidInputException e) {
                    context.getForWhom().sendRawMessage(UI.color(e.getMessage(), UI.COLOR_ERROR));
                    return this;
                }
            }
        }).withLocalEcho(false).buildConversation(player);

        conversation.begin();
    }
    
    public static void getText(Player player, Consumer<List<String>> callback) {
        Menu menu = new Menu("Place written book", MenuSize.MD);

        // Add background items
        MenuItemBuilder gray = MenuItems.BACKGROUND.clone();
        MenuItemBuilder green = new MenuItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE);
        
        menu.setItem(gray, 0);
        menu.setItem(gray, 1);
        menu.setItem(gray, 2);
        menu.setItem(gray, 3);
        menu.setItem(green, 4);
        menu.setItem(green, 5);
        menu.setItem(green, 6);
        menu.setItem(gray, 7);
        menu.setItem(gray, 8);
        
        menu.setItem(gray, 9);
        menu.setItem(gray, 10);
        menu.setItem(gray, 11);
        menu.setItem(gray, 12);
        menu.setItem(green, 13);
        menu.setItem(green, 15);
        menu.setItem(gray, 16);
        menu.setItem(gray, 17);
        
        menu.setItem(gray, 18);
        menu.setItem(gray, 19);
        menu.setItem(gray, 20);
        menu.setItem(gray, 21);
        menu.setItem(green, 22);
        menu.setItem(green, 23);
        menu.setItem(green, 24);
        menu.setItem(gray, 25);
        menu.setItem(gray, 26);
        
        // Add cancel item
        MenuItemBuilder backItem = MenuItems.BACK.clone();

        backItem.setClickListener(backClick -> {
            UI.playSound(player, UI.SOUND_CLICK);
            callback.accept(null);
        });

        menu.setItem(backItem, 11);
        
        // Add menu click listener
        menu.addClickListener(click -> {
            int slot = click.getSlot();
            
            if(slot == 14) {
                InventoryAction action = click.getAction();
                ItemStack item = click.getCursor();
                Material type = item.getType();
                
                if(action == InventoryAction.PLACE_ALL && type == Material.WRITTEN_BOOK) {
                    BookMeta meta = (BookMeta) item.getItemMeta();

                    UI.playSound(player, UI.SOUND_CLICK);
                    click.setCancelled(false);
                    
                    Bukkit.getScheduler().runTaskLater(SpigotUtils.getInstance(), () -> {
                        callback.accept(meta.getPages());
                    }, 1);
                } else {
                    UI.playSound(player, UI.SOUND_ERROR);
                }
            }
        });
        
        menu.open(player);
    }
    
}
