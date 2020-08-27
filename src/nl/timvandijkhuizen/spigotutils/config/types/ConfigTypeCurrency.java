package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.Currency;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.menu.MenuItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.PagedMenu;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class ConfigTypeCurrency implements ConfigType<Currency> {
    
    @Override
    public Currency getValue(Configuration config, ConfigOption<Currency> option) {
        try {
            String currencyCode = config.getString(option.getPath());
            return Currency.getInstance(currencyCode);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void setValue(Configuration config, ConfigOption<Currency> option, Currency value) {
        config.set(option.getPath(), value.getCurrencyCode());
    }

    @Override
    public String getValueLore(Configuration config, ConfigOption<Currency> option) {
        return option.getValue(config).getDisplayName();
    }

    @Override
    public boolean isValueEmpty(Configuration config, ConfigOption<Currency> option) {
        return option.getValue(config) == null;
    }

    @Override
    public void getValueInput(Player player, Consumer<Currency> callback) {
        PagedMenu menu = new PagedMenu("Select Currency", 3, 7, 1, 1);

        for (Currency currency : Currency.getAvailableCurrencies()) {
            MenuItemBuilder item = new MenuItemBuilder(Material.SUNFLOWER);

            item.setName(UI.color(currency.getDisplayName(), UI.PRIMARY_COLOR, ChatColor.BOLD));
            item.setLore(UI.color("Code: ", UI.TEXT_COLOR) + UI.color(currency.getCurrencyCode(), UI.SECONDARY_COLOR));

            item.setClickListener(event -> {
                UI.playSound(player, UI.CLICK_SOUND);
                callback.accept(currency);
            });

            menu.addPagedButton(item);
        }
        
        menu.open(player);
    }

}
