package nl.timvandijkhuizen.spigotutils.config.types;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class ConfigTypeBoolean implements ConfigType<Boolean> {

    @Override
    public Boolean getValue(OptionConfig config, ConfigOption<Boolean> option) {
        return config.getBoolean(option.getPath());
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<Boolean> option, Boolean value) {
        config.set(option.getPath(), value);
    }

    @Override
    public Boolean getValue(JsonObject json, ConfigOption<Boolean> option) {
        JsonElement element = json.get(option.getPath());
        
        // Check if json property exists
        if(element == null) {
            return null;
        }
        
        return element.getAsBoolean();
    }

    @Override
    public void setValue(JsonObject json, ConfigOption<Boolean> option, Boolean value) {
        json.addProperty(option.getPath(), value);
    }
    
    @Override
    public String[] getValueLore(Boolean value) {
        return new String[] { value ? "Yes" : "No" };
    }

    @Override
    public boolean isValueEmpty(Boolean value) {
        return value == null;
    }
    
    @Override
    public void getValueInput(Player player, Boolean value, Consumer<Boolean> callback) {
        callback.accept(!value);
    }

}