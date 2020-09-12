package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.HashMap;
import java.util.Map;

public class ClassHelper {

    private static Map<String, Class<?>> classCache = new HashMap<>();

    public static String toString(Class<?> clazz) {
        return clazz.getName();
    }
    
    public static Class<?> fromString(String str) {
        Class<?> cached = classCache.get(str);
        
        if(cached != null) {
            return cached;
        }
        
        try {
            Class<?> clazz = Class.forName(str);
            classCache.put(str, clazz);
            return clazz;
        } catch (Exception e) {
            return null;
        }
    }
    
}
