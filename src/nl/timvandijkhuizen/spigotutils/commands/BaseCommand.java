package nl.timvandijkhuizen.spigotutils.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.ui.UI;

public abstract class BaseCommand implements CommandExecutor {

    public static final String MESSAGE_NO_PERMISSION = "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake.";
    public static final String MESSAGE_ERROR = "An internal error occurred while attempting to perform this command.";

    private Map<String, BaseCommand> subCommandMap;

    /**
     * Returns the command label.
     * 
     * @return
     */
    public abstract String getCommand();

    /**
     * Returns the command usage.
     * 
     * @return
     */
    public abstract String getUsage();

    /**
     * Returns the permission required to execute this command. This can be
     * null.
     * 
     * @return
     */
    public String getPermission() {
        return null;
    }

    /**
     * Returns an array of subcommands.
     * 
     * @return
     */
    public BaseCommand[] getSubCommands() {
        return new BaseCommand[] {};
    }

    /**
     * Called when a player executes the command.
     * 
     * @param player
     * @param args
     * @throws Throwable
     */
    public abstract void onPlayerUse(Player player, String[] args) throws Throwable;

    /**
     * Call when the console executes the command.
     * 
     * @param console
     * @param args
     * @throws Throwable
     */
    public abstract void onConsoleUse(CommandSender console, String[] args) throws Throwable;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return handleCommand(sender, args);
    }

    /**
     * Handle a command being executed.
     * 
     * @param sender
     * @param args
     * @return
     */
    private boolean handleCommand(CommandSender sender, String[] args) {
        Map<String, BaseCommand> subCommandMap = getSubCommandMap();

        if (args.length >= 1) {
            BaseCommand subCommand = subCommandMap.get(args[0].toLowerCase());

            if (subCommand != null) {
                String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
                return subCommand.handleCommand(sender, subCommandArgs);
            }
        }

        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String permission = getPermission();

                if (permission == null || player.hasPermission(permission)) {
                    onPlayerUse(player, args);
                } else {
                    player.sendMessage(UI.color(MESSAGE_NO_PERMISSION, UI.COLOR_ERROR));
                }
            } else {
                onConsoleUse(sender, args);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            sender.sendMessage(UI.color(MESSAGE_ERROR, UI.COLOR_ERROR));
        }

        return true;
    }

    /**
     * Get a map of subcommands.
     * 
     * @return
     */
    private Map<String, BaseCommand> getSubCommandMap() {
        if (subCommandMap == null) {
            subCommandMap = new HashMap<>();

            for (BaseCommand subCommand : getSubCommands()) {
                subCommandMap.put(subCommand.getCommand(), subCommand);
            }
        }

        return subCommandMap;
    }

}
