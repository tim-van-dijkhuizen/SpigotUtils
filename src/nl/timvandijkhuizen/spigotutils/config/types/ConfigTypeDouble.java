package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.InputHelper;
import nl.timvandijkhuizen.spigotutils.menu.Menu;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeDouble implements ConfigType<Double> {

    @Override
    public Double getValue(OptionConfig config, ConfigOption<Double> option) {
        return config.getDouble(option.getPath());
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Double> option, Double value) {
        config.set(option.getPath(), value);
    }
    
    @Override
    public String getRawValue(OptionConfig config, ConfigOption<Double> option) {
        Double value = getValue(config, option);
        return value.toString();
    }

    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<Double> option) {
        return getRawValue(config, option);
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<Double> option) {
        return !config.contains(option.getPath());
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<Double> option, MenuClick event, Consumer<Double> callback) {
        Player player = event.getPlayer();
        Menu menu = event.getMenu();
        
        menu.close(player);
        
        InputHelper.getNumber(player, UI.color("What should be the new value?", UI.COLOR_PRIMARY), (ctx, input) -> {
            callback.accept(input.doubleValue());
            return null;
        });
    }

}
