package mysticmc.mystick.handler;

import mysticmc.mystick.commands.FlyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandsHandler {

    public static void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("fly").setExecutor(new FlyCommand());
    }
}
