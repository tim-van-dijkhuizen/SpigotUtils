package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.InputHelper;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeInteger implements ConfigType<Integer> {

    @Override
    public Integer getValue(OptionConfig config, ConfigOption<Integer> option) {
        return config.getInt(option.getPath());
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Integer> option, Integer value) {
        config.set(option.getPath(), value);
    }
    
    @Override
    public String getRawValue(OptionConfig config, ConfigOption<Integer> option) {
        int value = getValue(config, option);
        return Integer.toString(value);
    }

    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<Integer> option) {
        return getRawValue(config, option);
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<Integer> option) {
        return !config.contains(option.getPath());
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<Integer> option, MenuClick event, Consumer<Integer> callback) {
        Player player = event.getPlayer();

        InputHelper.getNumber(player, UI.color("What should be the new value?", UI.COLOR_PRIMARY), (ctx, input) -> {
            callback.accept(input.intValue());
            return null;
        });
    }

}
