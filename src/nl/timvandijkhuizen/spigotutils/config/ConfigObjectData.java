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
    
    public Integer getInteger(String key) {
        return getValue(key, raw -> (Integer) raw);
    }
    
    public Float getFloat(String key) {
        return getValue(key, raw -> (Float) raw);
    }
    
    public Double getDouble(String key) {
        return getValue(key, raw -> (Double) raw);
    }
    
    public Long getLong(String key) {
        return getValue(key, raw -> (Long) raw);
    }
    
    public Boolean getBoolean(String key) {
        return getValue(key, raw -> (Boolean) raw);
    }
    
    public UUID getUUID(String key) {
        return getValue(key, raw -> (UUID) raw);
    }
    
    public void set(String key, Object value) {
        params.put(key, value);
    }
    
    private <T> T getValue(String key, Function<Object, T> converter) {
        Object value = params.get(key);
        
        if(value == null) {
            return null;
        }
        
        try {
            return converter.apply(value);
        } catch(Exception e) {
            return null;
        }
    }
    
    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(params);
    }
    
}