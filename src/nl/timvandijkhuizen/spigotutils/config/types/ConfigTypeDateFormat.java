package nl.timvandijkhuizen.spigotutils.config.types;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.InputHelper;
import nl.timvandijkhuizen.spigotutils.input.InvalidInputException;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
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
    public String getRawValue(OptionConfig config, ConfigOption<SimpleDateFormat> option) {
        return !isValueEmpty(config, option) ? getValue(config, option).toPattern() : "";
    }
    
    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<SimpleDateFormat> option) {
        return getRawValue(config, option);
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<SimpleDateFormat> option) {
        return getValue(config, option) == null;
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<SimpleDateFormat> option, MenuClick event, Consumer<SimpleDateFormat> callback) {
        Player player = event.getPlayer();
        Menu menu = event.getMenu();
        
        menu.close(player);

        InputHelper.getString(player, UI.color("What should be the new value?", UI.COLOR_PRIMARY), (ctx, input) -> {
            try {
                callback.accept(new SimpleDateFormat(input));
                return null;
            } catch(IllegalArgumentException e) {
                throw new InvalidInputException("You must specify a valid dateformat, please try again.");
            }
        });
    }

}

