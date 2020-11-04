package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.Currency;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.menu.MenuClick;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItems;
import nl.timvandijkhuizen.spigotutils.menu.types.PagedMenu;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeCurrency implements ConfigType<Currency> {

    @Override
    public Currency getValue(OptionConfig config, ConfigOption<Currency> option) {
        try {
            String currencyCode = config.getString(option.getPath());
            return Currency.getInstance(currencyCode);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Currency> option, Currency value) {
        config.set(option.getPath(), value != null ? value.getCurrencyCode() : null);
    }

    @Override
    public String getRawValue(OptionConfig config, ConfigOption<Currency> option) {
        return !isValueEmpty(config, option) ? getValue(config, option).getCurrencyCode() : "";
    }
    
    @Override
    public String getDisplayValue(OptionConfig config, ConfigOption<Currency> option) {
        return !isValueEmpty(config, option) ? getValue(config, option).getDisplayName() : "";
    }
    
    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<Currency> option) {
        return !config.contains(option.getPath()) || getValue(config, option) == null;
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<Currency> option, MenuClick event, Consumer<Currency> callback) {
        PagedMenu menu = new PagedMenu("Select Currency", 3, 7, 1, 1, 1, 5, 7);
        Player player = event.getPlayer();
        Currency selected = getValue(config, option);

        for (Currency currency : Currency.getAvailableCurrencies()) {
            MenuItemBuilder item = new MenuItemBuilder(XMaterial.SUNFLOWER);

            item.setName(UI.color(currency.getDisplayName(), UI.COLOR_PRIMARY, ChatColor.BOLD));
            item.setLore(UI.color("Code: ", UI.COLOR_TEXT) + UI.color(currency.getCurrencyCode(), UI.COLOR_SECONDARY));

            if(selected != null && currency.getCurrencyCode().equals(selected.getCurrencyCode())) {
                item.addEnchantGlow();
            }
            
            item.setClickListener(itemClick -> {
                UI.playSound(player, UI.SOUND_CLICK);
                callback.accept(currency);
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
