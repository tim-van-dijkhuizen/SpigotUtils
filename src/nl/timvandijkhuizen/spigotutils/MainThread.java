package nl.timvandijkhuizen.spigotutils;

import org.bukkit.Bukkit;

public class MainThread {

    private static PluginBase plugin;

    public static void execute(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    public static void setPlugin(PluginBase plugin) {
        MainThread.plugin = plugin;
    }

}
