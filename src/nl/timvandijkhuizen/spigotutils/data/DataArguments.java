package nl.timvandijkhuizen.spigotutils.data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DataArguments {

    private Object[] arguments;

    public DataArguments(Object... arguments) {
        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    private <T> T getValue(int index, T defaultValue) {
        if (arguments.length <= index) {
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

    public Integer getInteger(int index) {
        return getValue(index);
    }

    public Integer getInteger(int index, int defaultValue) {
        return getValue(index, defaultValue);
    }

    public Double getDouble(int index) {
        return getValue(index);
    }

    public Double getDouble(int index, double defaultValue) {
        return getValue(index, defaultValue);
    }

    public Float getFloat(int index) {
        return getValue(index);
    }

    public Float getFloat(int index, float defaultValue) {
        return getValue(index, defaultValue);
    }

    public Long getLong(int index) {
        return getValue(index);
    }

    public Long getLong(int index, long defaultValue) {
        return getValue(index, defaultValue);
    }

    public Boolean getBoolean(int index) {
        return getValue(index);
    }

    public Boolean getBoolean(int index, boolean defaultValue) {
        return getValue(index, defaultValue);
    }

    public Byte getByte(int index) {
        return getValue(index);
    }

    public Byte getByte(int index, byte defaultValue) {
        return getValue(index, defaultValue);
    }

    public Short getShort(int index) {
        return getValue(index);
    }

    public Short getShort(int index, short defaultValue) {
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
