package nl.timvandijkhuizen.spigotutils.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonHelper {

    public static final Gson GSON = new Gson();

    public static String toJson(Object object) {
        if(object == null) {
            return null;
        }
        
        return GSON.toJson(object);
    }

    public static JsonObject fromJson(String json) {
        return fromJson(json, JsonObject.class);
    }
    
    public static <T> T fromJson(String json, Class<T> type) {
        if(json == null) {
            return null;
        }
        
        return GSON.fromJson(json, type);
    }
    
    public static JsonObject toJsonObject(Object object) {
        String json = toJson(object);
        return fromJson(json);
    }

}
