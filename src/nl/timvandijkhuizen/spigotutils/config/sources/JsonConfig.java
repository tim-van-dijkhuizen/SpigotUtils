package nl.timvandijkhuizen.spigotutils.config.sources;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.bukkit.configuration.MemoryConfiguration;

import com.google.gson.JsonObject;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class JsonConfig extends MemoryConfiguration implements OptionConfig {

    private JsonObject json;
    private Collection<ConfigOption<?>> options = new LinkedHashSet<>();
    
    public JsonConfig(JsonObject json) {
        this.json = json;
        
        for(ConfigOption<?> option : getOptions()) {
            set(option.getPath(), option.getValue(json));
        }
    }
    
    public JsonObject getJson() {
        return json;
    }

    @Override
    public void addOption(ConfigOption<?> option) {
        options.add(option);
    }
    
    @Override
    public Collection<ConfigOption<?>> getOptions() {
        return options;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> ConfigOption<T> getOption(String path) {
        return options.stream().filter(i -> i.getPath().equals(path)).map(i -> (ConfigOption<T>) i).findFirst().orElse(null);
    }
    
}
