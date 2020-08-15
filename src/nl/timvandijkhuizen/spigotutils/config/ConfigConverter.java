package nl.timvandijkhuizen.spigotutils.config;

public abstract class ConfigConverter<T> {

    /**
     * Returns the class of the data type.
     * 
     * @return
     */
    public abstract Class<T> getType();

    /**
     * Serializes the value.
     * 
     * @param object
     * @return
     */
    protected abstract Object serialize(T object);

    /**
     * Deserializes the value.
     * 
     * @param input
     * @return
     */
    protected abstract T deserialize(Object input);

    @SuppressWarnings("unchecked")
    public T serializeObject(Object object) {
        return deserialize((T) object);
    }

}
