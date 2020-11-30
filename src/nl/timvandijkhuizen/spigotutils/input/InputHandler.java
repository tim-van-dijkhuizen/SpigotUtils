package nl.timvandijkhuizen.spigotutils.input;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

public interface InputHandler<T> {

    Prompt handle(ConversationContext ctx, T value) throws InvalidInputException;
    
}
