package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.InputHelper;
import nl.timvandijkhuizen.spigotutils.input.InvalidInputException;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeDomain extends ConfigTypeString {

    public static final Pattern DOMAIN_REGEX = Pattern.compile("^[0-9\\p{L}][0-9\\p{L}-\\.]{1,61}[0-9\\p{L}]\\.[0-9\\p{L}][\\p{L}-]*[0-9\\p{L}]+$");

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<String> option, MenuClick event, Consumer<String> callback) {
        Player player = event.getPlayer();
        Menu menu = event.getMenu();
        
        menu.close(player);
        
        InputHelper.getString(player, UI.color("What should be the new value?", UI.COLOR_PRIMARY), (ctx, input) -> {
            Matcher domainMatch = DOMAIN_REGEX.matcher(input);

            if (!domainMatch.matches()) {
                throw new InvalidInputException("You must specify a valid domain, please try again.");
            }

            callback.accept(input);
            return null;
        });
    }

}
