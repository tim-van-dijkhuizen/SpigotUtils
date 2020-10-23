package nl.timvandijkhuizen.spigotutils.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class ConfigObjectData {

    private Map<String, Object> params;

    public ConfigObjectData(Map<?, ?> rawObject) {
        this.params = (Map<String, Object>) rawObject;
    }

    public ConfigObjectData() {
        this.params = new HashMap<>();
    }

    public String getString(String key) {
        return getValue(key, raw -> (String) raw);
    }
    
    public char getChar(String key) {
        return getValue(key, raw -> raw.charAt(0));
    }

    public int getInteger(String key) {
        return getValue(key, raw -> Integer.valueOf(raw));
    }

    public float getFloat(String key) {
        return getValue(key, raw -> Float.valueOf(raw));
    }

    public double getDouble(String key) {
        return getValue(key, raw -> Double.valueOf(raw));
    }

    public long getLong(String key) {
        return getValue(key, raw -> Long.valueOf(raw));
    }

    public boolean getBoolean(String key) {
        return getValue(key, raw -> Boolean.valueOf(raw));
    }

    public UUID getUUID(String key) {
        return getValue(key, raw -> UUID.fromString(raw));
    }

    public void set(String key, Object value) {
        params.put(key, value);
    }

    private <T> T getValue(String key, Function<String, T> converter) {
        Object value = params.get(key);

        try {
            return converter.apply(value.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Value cannot be parsed into the requested type");
        }
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(params);
    }

}