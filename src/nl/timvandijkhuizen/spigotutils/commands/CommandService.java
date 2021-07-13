package nl.timvandijkhuizen.spigotutils.commands;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.command.PluginCommand;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.services.Service;

public class CommandService implements Service {

    private PluginBase plugin;
    private Set<BaseCommand> commands = new LinkedHashSet<>();

    public CommandService(PluginBase plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() throws Throwable {
        for(BaseCommand executor : commands) {
            PluginCommand command = plugin.getCommand(executor.getCommand());
            
            if(command != null) {
                command.setExecutor(executor);
            }
        }
    }
    
    public void register(BaseCommand command) {
        commands.add(command);
    }

    public Set<BaseCommand> getCommands() {
        return commands;
    }

}
