package nl.timvandijkhuizen.spigotutils.helpers;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.SpigotUtils;
import nl.timvandijkhuizen.spigotutils.input.InputHandler;
import nl.timvandijkhuizen.spigotutils.input.InvalidInputException;
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
    
}
