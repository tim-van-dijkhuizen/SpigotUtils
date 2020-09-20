package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypePassword implements ConfigType<String> {

    public static final String PASSWORD_CHARACTER = "•";
    
    @Override
    public String getValue(OptionConfig config, ConfigOption<String> option) {
        return config.getString(option.getPath());
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<String> option, String value) {
        config.set(option.getPath(), value);
    }
    
    @Override
    public String getValueLore(OptionConfig config, ConfigOption<String> option) {
        return !isValueEmpty(config, option) ? StringUtils.repeat(PASSWORD_CHARACTER, getValue(config, option).length()) : "";
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<String> option) {
        String value = getValue(config, option);
        return value == null || value.length() == 0;
    }
    
    @Override
    public void getValueInput(OptionConfig config, ConfigOption<String> option, Player player, Consumer<String> callback) {
        ConversationFactory factory = new ConversationFactory(PluginBase.getInstance());

        Conversation conversation = factory.withFirstPrompt(new StringPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return UI.color("What should be the new value?", UI.COLOR_PRIMARY);
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
