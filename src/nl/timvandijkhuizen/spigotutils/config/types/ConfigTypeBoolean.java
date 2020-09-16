package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class ConfigTypeBoolean implements ConfigType<Boolean> {

    @Override
    public Boolean getValue(OptionConfig config, ConfigOption<Boolean> option) {
        return config.getBoolean(option.getPath());
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Boolean> option, Boolean value) {
        config.set(option.getPath(), value);
    }
    
    @Override
    public String getValueLore(Boolean value) {
        return (value != null && value) ? "Yes" : "No";
    }

    @Override
    public boolean isValueEmpty(Boolean value) {
        return value == null;
    }
    
    @Override
    public void getValueInput(Player player, Boolean value, Consumer<Boolean> callback) {
        callback.accept((value != null && value) ? false : true);
    }

}