package nl.timvandijkhuizen.spigotutils.data;

public class TypedValue<T> {

    protected T value;

    public TypedValue(T value) {
        this.value = value;
    }

    public TypedValue() {
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

}
