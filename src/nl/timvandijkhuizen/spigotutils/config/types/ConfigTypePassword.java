package nl.timvandijkhuizen.spigotutils.config.types;

import org.apache.commons.lang.StringUtils;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class ConfigTypePassword extends ConfigTypeString {

    public static final String PASSWORD_CHARACTER = "•";

    @Override
    public String getValueLore(OptionConfig config, ConfigOption<String> option) {
        return !isValueEmpty(config, option) ? StringUtils.repeat(PASSWORD_CHARACTER, getValue(config, option).length()) : "";
    }
    
}
