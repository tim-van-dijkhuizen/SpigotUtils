package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.LocaleHelper;
import nl.timvandijkhuizen.spigotutils.menu.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.PagedMenu;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeLocale implements ConfigType<Locale> {

    public static final Locale[] LOCALES = Arrays.stream(Locale.getAvailableLocales()).filter(i -> i != Locale.ROOT).toArray(Locale[]::new);
    
    @Override
    public Locale getValue(OptionConfig config, ConfigOption<Locale> option) {
        String rawLocale = config.getString(option.getPath());
        return LocaleHelper.deserializeLocale(rawLocale);
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Locale> option, Locale value) {
        config.set(option.getPath(), LocaleHelper.serializeLocale(value));
    }

    @Override
    public Locale getValue(JsonObject json, ConfigOption<Locale> option) {
        JsonElement element = json.get(option.getPath());
        
        // Check if json property exists
        if(element == null) {
            return null;
        }
        
        return LocaleHelper.deserializeLocale(element.getAsString());
    }

    @Override
    public void setValue(JsonObject json, ConfigOption<Locale> option, Locale value) {
        json.addProperty(option.getPath(), LocaleHelper.serializeLocale(value));
    }
    
    @Override
    public String[] getValueLore(Locale value) {
        return new String[] { value.getDisplayName() };
    }

    @Override
    public boolean isValueEmpty(Locale value) {
        return value == null;
    }

    @Override
    public void getValueInput(Player player, Locale value, Consumer<Locale> callback) {
        PagedMenu menu = new PagedMenu("Select Locale", 3, 7, 1, 1);

        for (Locale locale : LOCALES) {
            MenuItemBuilder item = new MenuItemBuilder(Material.SUNFLOWER);
            Currency currency = Currency.getInstance(locale);

            // Format values
            String localeValue = LocaleHelper.serializeLocale(locale);
            String currencyValue = currency.getDisplayName() + "(" + currency.getCurrencyCode() + ")";
            
            item.setName(UI.color(locale.getDisplayName(), UI.PRIMARY_COLOR, ChatColor.BOLD));
            item.setLore(UI.color("Code: ", UI.TEXT_COLOR) + UI.color(localeValue, UI.SECONDARY_COLOR));
            item.setLore(UI.color("Currency: ", UI.TEXT_COLOR) + UI.color(currencyValue, UI.SECONDARY_COLOR));

            item.setClickListener(event -> {
                UI.playSound(player, UI.CLICK_SOUND);
                callback.accept(locale);
            });

            menu.addPagedButton(item);
        }
        
        menu.open(player);
    }
    

    
}
