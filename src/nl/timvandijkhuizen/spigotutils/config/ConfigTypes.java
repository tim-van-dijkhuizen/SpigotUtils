package nl.timvandijkhuizen.spigotutils.config;

import java.util.Currency;
import java.util.Locale;

import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeBoolean;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeCurrency;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeDomain;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeInteger;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeLocale;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeMessage;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypePassword;
import nl.timvandijkhuizen.spigotutils.config.types.ConfigTypeString;

public class ConfigTypes {

    public static final ConfigType<String> STRING = new ConfigTypeString();
    public static final ConfigType<Integer> INTEGER = new ConfigTypeInteger();
    public static final ConfigType<Boolean> BOOLEAN = new ConfigTypeBoolean();
    public static final ConfigType<String> PASSWORD = new ConfigTypePassword();
    public static final ConfigType<Currency> CURRENCY = new ConfigTypeCurrency();
    public static final ConfigType<Locale> LOCALE = new ConfigTypeLocale();
    public static final ConfigType<String> DOMAIN = new ConfigTypeDomain();
    public static final ConfigType<String> MESSAGE = new ConfigTypeMessage();

}
