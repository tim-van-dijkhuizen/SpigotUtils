# Command API
A basic API for creating commands in an object oriented way.

### Command example
```java
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.commands.BaseCommand;
import nl.timvandijkhuizen.spigotutils.ui.UI;

public class CommandAccount extends BaseCommand {

    @Override
    public String getCommand() {
        return "test";
    }

    @Override
    public String getUsage() {
        return "/test <param>";
    }

    @Override
    public void onPlayerUse(Player player, String[] args) throws Throwable {
        player.sendMessage("Player: " + args[0]);
    }

    @Override
    public void onConsoleUse(CommandSender console, String[] args) throws Throwable {
        player.sendMessage("Console: " + args[0]);
    }

}
```

### Registering your commands

```java
import nl.timvandijkhuizen.spigotutils.commands.CommandService;
import nl.timvandijkhuizen.spigotutils.services.Service;

public class TestPlugin extends PluginBase {

    @Override
    public Service[] registerServices() throws Throwable {
        CommandService commandService = new CommandService(this);
        
        commandService.register(new CommandTest());
        
        return new Service[] {
            commandService
        };
    }

}
```
