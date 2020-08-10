package nl.timvandijkhuizen.spigotutils.commands;

import nl.timvandijkhuizen.spigotutils.PluginBase;
import nl.timvandijkhuizen.spigotutils.services.Service;

public class CommandService implements Service {

	private PluginBase plugin;
	
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
	}
	
}
