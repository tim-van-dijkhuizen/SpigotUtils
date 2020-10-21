package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.Locale;

import org.bukkit.entity.Player;

public class LocaleHelper {

    public static String serializeLocale(Locale value) {
        return value.getLanguage() + "_" + value.getCountry();
    }

    public static Locale deserializeLocale(String value) {
        try {
            String[] parts = value.split("_");
            String language = parts[0];
            String country = parts[1];

            return new Locale(language, country);
        } catch (Exception e) {
            return null;
        }
    }

    public static Locale getUserLocale(Player player) {
        return deserializeLocale(player.getLocale());
    }

}
