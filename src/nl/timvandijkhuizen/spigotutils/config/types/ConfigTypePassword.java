package nl.timvandijkhuizen.spigotutils.config.types;

import org.apache.commons.lang.StringUtils;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class ConfigTypePassword extends ConfigTypeString {

    public static final String PASSWORD_CHARACTER = "•";

    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<String> option) {
        return StringUtils.repeat(PASSWORD_CHARACTER, getRawValue(config, option).length());
    }

}
