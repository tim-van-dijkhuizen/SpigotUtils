package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.function.BiFunction;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.PluginBase;

public class InputHelper {

    public static void getString(Player player, String question, BiFunction<ConversationContext, String, Prompt> callback) {
        ConversationFactory factory = new ConversationFactory(PluginBase.getInstance());

        Conversation conversation = factory.withFirstPrompt(new StringPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return question;
            }

            @Override
            public Prompt acceptInput(ConversationContext context, String input) {
                return callback.apply(context, input);
            }
        }).withLocalEcho(false).buildConversation(player);

        conversation.begin();
    }
    
    public static void getNumber(Player player, String question, BiFunction<ConversationContext, Number, Prompt> callback) {
        ConversationFactory factory = new ConversationFactory(PluginBase.getInstance());

        Conversation conversation = factory.withFirstPrompt(new NumericPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return question;
            }

            @Override
            protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
                return callback.apply(context, input);
            }
        }).withLocalEcho(false).buildConversation(player);

        conversation.begin();
    }
    
}
