package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.bukkit.configuration.Configuration;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeString implements ConfigType<String> {

    @Override
    public String getValue(Configuration config, ConfigOption<String> option) {
        return config.getString(option.getPath());
    }

    @Override
    public void setValue(Configuration config, ConfigOption<String> option, String value) {
        config.set(option.getPath(), value);
    }

    @Override
    public String getValueLore(Configuration config, ConfigOption<String> option) {
        return option.getValue(config);
    }

    @Override
    public boolean isValueEmpty(Configuration config, ConfigOption<String> option) {
        String value = option.getValue(config);
        return value == null || value.length() == 0;
    }
    
    @Override
    public void getValueInput(Player player, Consumer<String> callback) {
        ConversationFactory factory = new ConversationFactory(PluginBase.getInstance());

        Conversation conversation = factory.withFirstPrompt(new StringPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return UI.color("What should be the new value?", UI.PRIMARY_COLOR);
            }

            @Override
            public Prompt acceptInput(ConversationContext context, String input) {
                callback.accept(input);
                return null;
            }
        }).withLocalEcho(false).buildConversation(player);

        player.closeInventory();
        conversation.begin();
    }

}
