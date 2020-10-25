package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;

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
    public String getRawValue(OptionConfig config, ConfigOption<Boolean> option) {
        return getValue(config, option) ? "true" : "false";
    }
    
    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<Boolean> option) {
        return getValue(config, option) ? "Yes" : "No";
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<Boolean> option) {
        return false;
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<Boolean> option, MenuItemClick event, Consumer<Boolean> callback) {
        callback.accept(!getValue(config, option));
    }

}