package nl.timvandijkhuizen.spigotutils.config.sources;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;

public class JsonConfig implements Configuration, OptionConfig {

    private JsonObject json;
    private Collection<ConfigOption<?>> options = new LinkedHashSet<>();
    
    public JsonConfig(JsonObject json) {
        this.json = json;
    }
    
    public JsonConfig() {
        this.json = new JsonObject();
    }
    
    public JsonObject getJson() {
        return json;
    }
    
    @Override
    public void addOption(ConfigOption<?> option) {
        options.add(option);
    }
    
    @Override
    public void addOptions(Collection<ConfigOption<?>> options) {
        this.options.addAll(options);
    }
    
    @Override
    public Collection<ConfigOption<?>> getOptions() {
        return options;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> ConfigOption<T> getOption(String path) {
        return getOptions().stream().filter(i -> i.getPath().equals(path)).map(i -> (ConfigOption<T>) i).findFirst().orElse(null);
    }
    
    @Override
    public void setDefaultOptions() {
        for(ConfigOption<?> option : getOptions()) {
            if(option.isValueEmpty(this)) {
                option.resetValue(json);
            }
        }
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(String path) {
        return json.get(path) != null;
    }

    @Override
    public boolean contains(String path, boolean ignoreDefault) {
        return contains(path);
    }

    @Override
    public boolean isSet(String path) {
        return contains(path);
    }

    @Override
    public String getCurrentPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Configuration getRoot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConfigurationSection getParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(String path, Object def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(String path, Object value) {
        if(value instanceof Boolean) {
            json.addProperty(path, (Boolean) value);
        } else if(value instanceof Character) {
            json.addProperty(path, (Character) value);
        } else if(value instanceof Number) {
            json.addProperty(path, (Number) value);
        } else if(value instanceof String) {
            json.addProperty(path, (String) value);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    @Override
    public ConfigurationSection createSection(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getString(String path) {
        JsonElement element = json.get(path);
        
        if(element == null) {
            return null;
        }
        
        return element.getAsString();
    }

    @Override
    public String getString(String path, String def) {
        return getString(path);
    }

    @Override
    public boolean isString(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getInt(String path) {
        JsonElement element = json.get(path);
        
        if(element == null) {
            return 0;
        }
        
        return element.getAsInt();
    }

    @Override
    public int getInt(String path, int def) {
        return getInt(path);
    }

    @Override
    public boolean isInt(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean(String path) {
        JsonElement element = json.get(path);
        
        if(element == null) {
            return false;
        }
        
        return element.getAsBoolean();
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return getBoolean(path);
    }

    @Override
    public boolean isBoolean(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getDouble(String path) {
        JsonElement element = json.get(path);
        
        if(element == null) {
            return 0;
        }
        
        return element.getAsDouble();
    }

    @Override
    public double getDouble(String path, double def) {
        return getDouble(path);
    }

    @Override
    public boolean isDouble(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLong(String path) {
        JsonElement element = json.get(path);
        
        if(element == null) {
            return 0;
        }
        
        return element.getAsLong();
    }

    @Override
    public long getLong(String path, long def) {
        return getLong(path);
    }

    @Override
    public boolean isLong(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getStringList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Double> getDoubleList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Float> getFloatList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Long> getLongList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Byte> getByteList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Character> getCharacterList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Short> getShortList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<?, ?>> getMapList(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getObject(String path, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getObject(String path, Class<T> clazz, T def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz, T def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector getVector(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector getVector(String path, Vector def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isVector(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOfflinePlayer(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack getItemStack(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack getItemStack(String path, ItemStack def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isItemStack(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color getColor(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color getColor(String path, Color def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isColor(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getLocation(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getLocation(String path, Location def) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLocation(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConfigurationSection getConfigurationSection(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isConfigurationSection(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConfigurationSection getDefaultSection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDefault(String path, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDefaults(Configuration defaults) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDefaults(Configuration defaults) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Configuration getDefaults() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConfigurationOptions options() {
        throw new UnsupportedOperationException();
    }
    
}
