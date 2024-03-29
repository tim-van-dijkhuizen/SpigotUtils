package nl.timvandijkhuizen.spigotutils.data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DataValue extends TypedValue<Object> {

    public DataValue(Object value) {
        this.value = value;
    }

    public DataValue() {
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

    @SuppressWarnings("unchecked")
    public <T> List<T> asList(Class<T> type) {
        return (List<T>) value;
    }

    public Set<?> asSet() {
        return (Set<?>) value;
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> asSet(Class<T> type) {
        return (Set<T>) value;
    }

    @SuppressWarnings("unchecked")
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
