package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.Currency;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
    public Currency getValue(JsonObject json, ConfigOption<Currency> option) {
        JsonElement element = json.get(option.getPath());
        
        // Check if json property exists
        if(element == null) {
            return null;
        }
        
        // Get and parse value
        try {
            return Currency.getInstance(element.getAsString());
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void setValue(JsonObject json, ConfigOption<Currency> option, Currency value) {
        json.addProperty(option.getPath(), value.getCurrencyCode());
    }
    
    @Override
    public String getValueLore(Currency value) {
        return value.getDisplayName();
    }

    @Override
    public boolean isValueEmpty(Currency value) {
        return value == null;
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
