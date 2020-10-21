package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeDomain extends ConfigTypeString {

    public static final Pattern DOMAIN_REGEX = Pattern.compile("^[0-9\\p{L}][0-9\\p{L}-\\.]{1,61}[0-9\\p{L}]\\.[0-9\\p{L}][\\p{L}-]*[0-9\\p{L}]+$");

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<String> option, MenuItemClick event, Consumer<String> callback) {
        ConversationFactory factory = new ConversationFactory(PluginBase.getInstance());
        Player player = event.getPlayer();

        Conversation conversation = factory.withFirstPrompt(new StringPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return UI.color("What should be the new value?", UI.COLOR_PRIMARY);
            }

            @Override
            public Prompt acceptInput(ConversationContext context, String input) {
                Matcher domainMatch = DOMAIN_REGEX.matcher(input);

                if (!domainMatch.matches()) {
                    context.getForWhom().sendRawMessage(UI.color("You must specify a valid domain, please try again.", UI.COLOR_ERROR));
                    return this;
                }

                callback.accept(input);
                return null;
            }
        }).withLocalEcho(false).buildConversation(player);

        player.closeInventory();
        conversation.begin();
    }

}
