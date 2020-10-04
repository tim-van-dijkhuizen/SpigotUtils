package nl.timvandijkhuizen.spigotutils.helpers;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.Bukkit;

import nl.timvandijkhuizen.spigotutils.PluginBase;

public class ThreadHelper {

    private static PluginBase plugin;

    public static void execute(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }
    
    public static <T> void executeAsync(Supplier<T> task, Consumer<T> callback) {
    	Bukkit.getScheduler().runTaskAsynchronously(PluginBase.getInstance(), () -> {
    		T value = task.get();
    		execute(() -> callback.accept(value));
    	});
    }

    public static void setPlugin(PluginBase plugin) {
        ThreadHelper.plugin = plugin;
    }

}
