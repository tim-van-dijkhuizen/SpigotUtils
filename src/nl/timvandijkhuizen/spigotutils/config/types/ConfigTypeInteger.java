package nl.timvandijkhuizen.spigotutils.config.types;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigIcon;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.ConfigurationException;
import nl.timvandijkhuizen.spigotutils.config.YamlConfig;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeInteger implements ConfigType<Integer> {

    @Override
    public Integer getValue(YamlConfig config, ConfigOption<Integer> option) throws ConfigurationException {
        String path = option.getPath();
        
        if(!config.isInt(path)) {
            throw new ConfigurationException("Value must be an integer");
        }
        
        return config.getInt(path);
    }

    @Override
    public void setValue(YamlConfig config, ConfigOption<Integer> option, Integer value) {
        config.set(option.getPath(), value);
    }

    @Override
    public MenuItemBuilder createMenuItem(YamlConfig config, ConfigOption<Integer> option) {
        ConfigIcon icon = option.getIcon();
        MenuItemBuilder item = new MenuItemBuilder(icon.getMaterial());
        int value = config.getOptionValue(option);
        
        item.setName(UI.color(icon.getName(), UI.PRIMARY_COLOR, ChatColor.BOLD));
        item.setLore(UI.color("Current value: ", UI.TEXT_COLOR) + UI.color(parseValue(value), UI.SECONDARY_COLOR));
        item.addLore("", UI.color("Left-click to edit this setting.", UI.SECONDARY_COLOR, ChatColor.ITALIC));
        
        // Set click listener
        item.setClickListener(event -> {
            ConversationFactory factory = new ConversationFactory(config.getPlugin());
            Player player = event.getPlayer();
            Menu menu = event.getMenu();

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

            Conversation conversation = factory.withFirstPrompt(new NumericPrompt() {
                @Override
                public String getPromptText(ConversationContext context) {
                    return UI.color("What should be the new value?", UI.PRIMARY_COLOR);
                }

                @Override
                protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
                    config.setOptionValue(option, input.intValue());
                    item.setLore(UI.color("Current value: ", UI.TEXT_COLOR) + UI.color(parseValue(input.intValue()), UI.SECONDARY_COLOR), 0);
                    menu.open(player);
                    
                    return null;
                }
            }).withLocalEcho(false).buildConversation(player);

            menu.close(player);
            conversation.begin();
        });
        
        return item;
    }
    
    private String parseValue(Integer value) {
        return "" + value;
    }

}
