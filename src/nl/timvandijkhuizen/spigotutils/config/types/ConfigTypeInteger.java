package nl.timvandijkhuizen.spigotutils.config.types;

import org.bukkit.Sound;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.ConfigurationException;
import nl.timvandijkhuizen.spigotutils.config.YamlConfig;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.MenuItemClick;
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
    public String getItemValue(YamlConfig config, ConfigOption<Integer> option) {
        return "" + config.getOptionValue(option);
    }

    @Override
    public void handleItemClick(YamlConfig config, ConfigOption<Integer> option, MenuItemClick event) {
        ConversationFactory factory = new ConversationFactory(config.getPlugin());
        MenuItemBuilder item = event.getItem();
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
                item.setLore(UI.color("Current value: ", UI.TEXT_COLOR) + UI.color(getItemValue(config, option), UI.SECONDARY_COLOR), 0);
                menu.open(player);
                
                return null;
            }
        }).withLocalEcho(false).buildConversation(player);

        menu.close(player);
        conversation.begin();
    }

}
