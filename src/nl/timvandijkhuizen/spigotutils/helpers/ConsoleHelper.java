package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.function.BiConsumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class ConsoleHelper {

    private static BiConsumer<LogType, String> logMethod = null;
    private static boolean devMode = false;
    
    public static void setDevMode(boolean devMode) {
        ConsoleHelper.devMode = devMode;
    }
    
    public static void setLogMethod(BiConsumer<LogType, String> logMethod) {
        ConsoleHelper.logMethod = logMethod;
    }

    public static void printInfo(String message) {
        print(LogType.INFO, message);
    }

    public static void printError(String message) {
        printError(message, null);
    }

    public static void printError(String message, Throwable error) {
        print(LogType.ERROR, message);

        if (error != null) {
            error.printStackTrace();
        }
    }
    
    public static void printDebug(String message) {
        print(LogType.DEBUG, message);
    }
    
    private static void print(LogType type, String message) {
        if(logMethod != null) {
            logMethod.accept(type, message);
            return;
        }
        
        // Check if we should ignore
        if(type != LogType.DEBUG || devMode) {
            Level level = Level.INFO;
            
            if(type == LogType.WARNING) {
                level = Level.WARNING;
            } else if(type == LogType.ERROR) {
                level = Level.SEVERE;
            }
            
            Bukkit.getLogger().log(level, message);
        }
    }
    
    public enum LogType {
        
        INFO, WARNING, ERROR, DEBUG
        
    }

}
