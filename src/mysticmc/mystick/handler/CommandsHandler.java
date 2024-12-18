package mysticmc.mystick.handler;

import mysticmc.mystick.commands.FlyCommand;
import mysticmc.mystick.commands.VanishCommand;
import mysticmc.mystick.commands.CiCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.craftbukkit.v1_21_R1.CraftServer;

import java.lang.reflect.Field;

public class CommandsHandler {

    public static void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("fly").setExecutor(new FlyCommand());
        
        plugin.getCommand("vanish").setExecutor(new VanishCommand()
        plugin.getCommand("v").setExecutor(new VanishCommand()

        plugin.getCommand("clearinventory").setExecutor(new CiCommand());
        plugin.getCommand("ci").setExecutor(new CiCommand());

        // Desregistrar comandos de outros plugins, se necessário
        unregisterCommand(plugin, "fly");

        unregisterCommand(plugin, "clearinventory");
        unregisterCommand(plugin, "ci"); // Alias

        unregisterCommand(plugin, "vanish")
        unregisterCommand(plugin, "v"); // Alias
    }

    public static void unregisterCommand(JavaPlugin plugin, String commandName) {
        try {
            CraftServer server = (CraftServer) Bukkit.getServer();
            Field commandMapField = CraftServer.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(server);

            Command command = commandMap.getCommand(commandName);
            if (command != null) {
                commandMap.getCommands().remove(commandName);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Erro ao desregistrar comando: " + commandName);
            e.printStackTrace();
        }
    }
}
