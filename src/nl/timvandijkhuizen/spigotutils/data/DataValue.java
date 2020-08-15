package nl.timvandijkhuizen.spigotutils.data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class DataValue {

    private Object value;

    public DataValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String asString() {
        return String.class.cast(value);
    }

    public int asInteger() {
        return Integer.class.cast(value);
    }

    public double asDouble() {
        return Double.class.cast(value);
    }

    public float asFloat() {
        return Float.class.cast(value);
    }

    public long asLong() {
        return Long.class.cast(value);
    }

    public boolean asBoolean() {
        return Boolean.class.cast(value);
    }

    public byte asByte() {
        return Byte.class.cast(value);
    }

    public short asShort() {
        return Short.class.cast(value);
    }

    public UUID asUUID() {
        return UUID.class.cast(value);
    }

    public List<?> asList() {
        return (List<?>) value;
    }

    public <T> List<T> asList(Class<T> type) {
        return (List<T>) value;
    }

    public <K, V> Map<K, V> asMap(Class<K> keyType, Class<V> valueType) {
        return (Map<K, V>) value;
    }

    public Map<?, ?> asMap() {
        return (Map<?, ?>) value;
    }

    public <T> T as(Class<T> type) {
        return type.cast(value);
    }

}
