package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class ConfigTypeBoolean implements ConfigType<Boolean> {

    @Override
    public Boolean getValue(OptionConfig config, ConfigOption<Boolean> option) {
        return config.getBoolean(option.getPath(), false);
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Boolean> option, Boolean value) {
        config.set(option.getPath(), value);
    }
    
    @Override
    public String getValueLore(OptionConfig config, ConfigOption<Boolean> option) {
        return getValue(config, option) ? "Yes" : "No";
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<Boolean> option) {
        return false;
    }
    
    @Override
    public void getValueInput(OptionConfig config, ConfigOption<Boolean> option, Player player, Consumer<Boolean> callback) {
        callback.accept(!getValue(config, option));
    }

}