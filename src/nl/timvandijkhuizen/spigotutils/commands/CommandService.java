package nl.timvandijkhuizen.spigotutils.commands;

import java.util.LinkedHashSet;
import java.util.Set;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.services.BaseService;

public class CommandService extends BaseService {

    private PluginBase plugin;
    private Set<BaseCommand> commands = new LinkedHashSet<>();

    public CommandService(PluginBase plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getHandle() {
        return "commands";
    }

    public void register(BaseCommand command) {
        plugin.getCommand(command.getCommand()).setExecutor(command);
        commands.add(command);
    }

    public Set<BaseCommand> getCommands() {
        return commands;
    }

}
