package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.logging.Level;

import org.bukkit.Bukkit;

public class ConsoleHelper {

    private static boolean devMode = true;

    public static void setDevMode(boolean devMode) {
        ConsoleHelper.devMode = devMode;
    }

    public static void printInfo(String message) {
        Bukkit.getLogger().log(Level.INFO, message);
    }

    public static void printError(String message) {
        printError(message, null);
    }

    public static void printError(String message, Throwable error) {
        Bukkit.getLogger().log(Level.WARNING, message);

        if (error != null && devMode) {
            error.printStackTrace();
        }
    }
    
    public static void printDebug(String message) {
        if(devMode) {
            Bukkit.getLogger().log(Level.INFO, message);
        }
    }

}
