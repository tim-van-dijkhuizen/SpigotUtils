package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypePassword implements ConfigType<String> {

    public static final String PASSWORD_CHARACTER = "•";
    
    @Override
    public String getValue(Configuration config, ConfigOption<String> option) {
        return config.getString(option.getPath());
    }

    @Override
    public void setValue(Configuration config, ConfigOption<String> option, String value) {
        config.set(option.getPath(), value);
    }

    @Override
    public String getValue(JsonObject json, ConfigOption<String> option) {
        JsonElement element = json.get(option.getPath());
        
        // Check if json property exists
        if(element == null) {
            return null;
        }
        
        return element.getAsString();
    }

    @Override
    public void setValue(JsonObject json, ConfigOption<String> option, String value) {
        json.addProperty(option.getPath(), value);
    }
    
    @Override
    public String getValueLore(String value) {
        return StringUtils.repeat(PASSWORD_CHARACTER, value.length());
    }

    @Override
    public boolean isValueEmpty(String value) {
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