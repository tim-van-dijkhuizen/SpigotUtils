package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.helpers.LocaleHelper;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.menu.types.PagedMenu;
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
        config.set(option.getPath(), value != null ? LocaleHelper.serializeLocale(value) : null);
    }

    @Override
    public String getRawValue(OptionConfig config, ConfigOption<Locale> option) {
        if(!isValueEmpty(config, option)) {
            Locale value = getValue(config, option);
            return LocaleHelper.serializeLocale(value);
        }
        
        return "";
    }
    
    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<Locale> option) {
        return !isValueEmpty(config, option) ? getValue(config, option).getDisplayName() : "";
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<Locale> option) {
        return !config.contains(option.getPath()) || getValue(config, option) == null;
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<Locale> option, MenuClick event, Consumer<Locale> callback) {
        PagedMenu menu = new PagedMenu("Select Locale", 3, 7, 1, 1, 1, 5, 7);
        Player player = event.getPlayer();
        Locale selected = getValue(config, option);
        String selectedValue = selected != null ? LocaleHelper.serializeLocale(selected) : null;

        for (Locale locale : LOCALES) {
            MenuItemBuilder item = new MenuItemBuilder(XMaterial.SUNFLOWER);
            Currency currency = Currency.getInstance(locale);

            // Format values
            String localeValue = LocaleHelper.serializeLocale(locale);
            String currencyValue = currency.getDisplayName() + "(" + currency.getCurrencyCode() + ")";

            item.setName(UI.color(locale.getDisplayName(), UI.COLOR_PRIMARY, ChatColor.BOLD));
            item.setLore(UI.color("Code: ", UI.COLOR_TEXT) + UI.color(localeValue, UI.COLOR_SECONDARY));
            item.setLore(UI.color("Currency: ", UI.COLOR_TEXT) + UI.color(currencyValue, UI.COLOR_SECONDARY));

            if(selectedValue != null && localeValue.equals(selectedValue)) {
                item.addEnchantGlow();
            }
            
            item.setClickListener(itemClick -> {
                UI.playSound(player, UI.SOUND_CLICK);
                callback.accept(locale);
            });

            menu.addPagedItem(item);
        }

        // Go back button
        MenuItemBuilder backButton = MenuItems.BACK.clone();

        backButton.setClickListener(backEvent -> {
            UI.playSound(player, UI.SOUND_CLICK);
            callback.accept(selected);
        });

        menu.setItem(backButton, menu.getSize().getSlots() - 9 + 3);
        
        menu.open(player);
    }

}
