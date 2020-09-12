package nl.timvandijkhuizen.spigotutils.menu;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class MenuArguments {

    private Object[] arguments;
    
    public MenuArguments(Object[] arguments) {
        this.arguments = arguments;
    }
    
    private <T> T getValue(int index, T defaultValue) {
        if(arguments.length <= index) {
            return defaultValue;
        }
        
        return (T) arguments[index];
    }
    
    private <T> T getValue(int index) {
        return getValue(index, null);
    }

    public String getString(int index) {
        return getValue(index);
    }
    
    public String getString(int index, String defaultValue) {
        return getValue(index, defaultValue);
    }

    public int getInteger(int index) {
        return getValue(index);
    }
    
    public int getInteger(int index, int defaultValue) {
        return getValue(index, defaultValue);
    }

    public double getDouble(int index) {
        return getValue(index);
    }
    
    public double getDouble(int index, double defaultValue) {
        return getValue(index, defaultValue);
    }

    public float getFloat(int index) {
        return getValue(index);
    }
    
    public float getFloat(int index, float defaultValue) {
        return getValue(index, defaultValue);
    }

    public long getLong(int index) {
        return getValue(index);
    }
    
    public long getLong(int index, long defaultValue) {
        return getValue(index, defaultValue);
    }

    public boolean getBoolean(int index) {
        return getValue(index);
    }
    
    public boolean getBoolean(int index, boolean defaultValue) {
        return getValue(index, defaultValue);
    }

    public byte getByte(int index) {
        return getValue(index);
    }
    
    public byte getByte(int index, byte defaultValue) {
        return getValue(index, defaultValue);
    }

    public short getShort(int index) {
        return getValue(index);
    }

    public short getShort(int index, short defaultValue) {
        return getValue(index, defaultValue);
    }
    
    public UUID getUUID(int index) {
        return getValue(index);
    }
    
    public UUID getUUID(int index, UUID defaultValue) {
        return getValue(index, defaultValue);
    }

    public <T> List<T> getList(int index) {
        return getValue(index);
    }
    
    public <T> List<T> getList(int index, List<T> defaultValue) {
        return getValue(index, defaultValue);
    }

    public <T> Set<T> getSet(int index) {
        return getValue(index);
    }
    
    public <T> Set<T> getSet(int index, Set<T> defaultValue) {
        return getValue(index, defaultValue);
    }

    public <K, V> Map<K, V> getMap(int index) {
        return getValue(index);
    }
    
    public <K, V> Map<K, V> getMap(int index, Map<K, V> defaultValue) {
        return getValue(index, defaultValue);
    }

    public <T> T get(int index) {
        return getValue(index);
    }
    
    public <T> T get(int index, T defaultValue) {
        return getValue(index, defaultValue);
    }
    
}
