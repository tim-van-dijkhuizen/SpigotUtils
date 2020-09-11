package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.logging.Level;

import org.bukkit.Bukkit;

public class ConsoleHelper {

    private static boolean showStacktraces;
    
    public static void showStacktraces(boolean showStacktraces) {
        ConsoleHelper.showStacktraces = showStacktraces;
    }
    
    public static void printInfo(String message) {
        Bukkit.getLogger().log(Level.INFO, message);
    }

    public static void printError(String message) {
        printError(message, null);
    }

    public static void printError(String message, Throwable error) {
        Bukkit.getLogger().log(Level.WARNING, message);

        if (error != null && showStacktraces) {
            error.printStackTrace();
        }
    }

}
