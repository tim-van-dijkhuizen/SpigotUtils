package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.function.BiConsumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class ConsoleHelper {

    private static boolean devMode = true;
    private static BiConsumer<Level, String> logMethod = null;

    public static void setDevMode(boolean devMode) {
        ConsoleHelper.devMode = devMode;
    }

    public static void printInfo(String message) {
        print(Level.INFO, message);
    }

    public static void printError(String message) {
        printError(message, null);
    }

    public static void printError(String message, Throwable error) {
        print(Level.SEVERE, message);

        if (error != null && devMode) {
            error.printStackTrace();
        }
    }
    
    public static void printDebug(String message) {
        if(devMode) {
            print(Level.INFO, message);
        }
    }
    
    public static void setLogMethod(BiConsumer<Level, String> logMethod) {
        ConsoleHelper.logMethod = logMethod;
    }
    
    private static void print(Level level, String message) {
        if(logMethod != null) {
            logMethod.accept(level, message);
        }
        
        Bukkit.getLogger().log(level, message);
    }

}
