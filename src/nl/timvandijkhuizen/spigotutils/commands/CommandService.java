package nl.timvandijkhuizen.spigotutils.commands;

import java.util.HashSet;
import java.util.Set;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.services.Service;

public class CommandService implements Service {

    private PluginBase plugin;
    private Set<BaseCommand> commands = new HashSet<>();

    public CommandService(PluginBase plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getHandle() {
        return "commands";
    }

    @Override
    public void load() throws Exception {

    }

    @Override
    public void unload() throws Exception {

    }

    public void register(BaseCommand command) {
        plugin.getCommand(command.getCommand()).setExecutor(command);
        commands.add(command);
    }
    
    public Set<BaseCommand> getCommands() {
        return commands;
    }

}
