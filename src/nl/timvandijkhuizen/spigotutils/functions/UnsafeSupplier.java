package nl.timvandijkhuizen.spigotutils.functions;

public interface UnsafeSupplier<T> {
	
	T get() throws Exception;
	
}
