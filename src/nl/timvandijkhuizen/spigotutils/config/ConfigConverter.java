package nl.timvandijkhuizen.spigotutils.config;

public abstract class ConfigConverter<T> {

	public abstract Class<T> getType();
	protected abstract Object serialize(T object);
	protected abstract T deserialize(Object input);
	
	@SuppressWarnings("unchecked")
	public T serializeObject(Object object) {
		return deserialize((T) object);
	}
	
}
