package nl.timvandijkhuizen.spigotutils.config.types;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

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
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeDateFormat implements ConfigType<SimpleDateFormat> {

    @Override
    public SimpleDateFormat getValue(OptionConfig config, ConfigOption<SimpleDateFormat> option) {
        String pattern = config.getString(option.getPath());
        
        if(pattern == null) {
            return null;
        }
        
        return new SimpleDateFormat(pattern);
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<SimpleDateFormat> option, SimpleDateFormat value) {
        config.set(option.getPath(), value.toPattern());
    }

    @Override
    public String getValueLore(OptionConfig config, ConfigOption<SimpleDateFormat> option) {
        return !isValueEmpty(config, option) ? getValue(config, option).toPattern() : "";
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<SimpleDateFormat> option) {
        return getValue(config, option) == null;
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<SimpleDateFormat> option, MenuItemClick event, Consumer<SimpleDateFormat> callback) {
        ConversationFactory factory = new ConversationFactory(PluginBase.getInstance());
        Player player = event.getPlayer();

        Conversation conversation = factory.withFirstPrompt(new StringPrompt() {
            @Override
            public String getPromptText(ConversationContext context) {
                return UI.color("What should be the new value?", UI.COLOR_PRIMARY);
            }

            @Override
            public Prompt acceptInput(ConversationContext context, String input) {
                try {
                    callback.accept(new SimpleDateFormat(input));
                    return null;
                } catch(IllegalArgumentException e) {
                    context.getForWhom().sendRawMessage(UI.color("You must specify a valid dateformat, please try again.", UI.COLOR_ERROR));
                    UI.playSound(player, UI.SOUND_ERROR);
                    return this;
                }
            }
        }).withLocalEcho(false).buildConversation(player);

        player.closeInventory();
        conversation.begin();
    }

}

