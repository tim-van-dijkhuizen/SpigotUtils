package nl.timvandijkhuizen.spigotutils.config;

import java.util.Currency;

import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeCurrency;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeInteger;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypePassword;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeString;

public class ConfigTypes {

    public static final ConfigType<String> STRING = new ConfigTypeString();
    public static final ConfigType<Integer> INTEGER = new ConfigTypeInteger();
    public static final ConfigType<String> PASSWORD = new ConfigTypePassword();
    public static final ConfigType<Currency> CURRENCY = new ConfigTypeCurrency();
    
}
