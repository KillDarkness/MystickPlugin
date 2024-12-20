package mysticmc.mystick.handler;

import mysticmc.mystick.commands.FlyCommand;
import mysticmc.mystick.commands.VanishCommand;
import mysticmc.mystick.commands.CiCommand;
import mysticmc.mystick.commands.EveryoneCommand;
import mysticmc.mystick.commands.FeedCommand;
import mysticmc.mystick.commands.HealCommand;
import mysticmc.mystick.config.CooldownConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class CommandsHandler {

    public static void registerCommands(JavaPlugin plugin) {
        // Criar instâncias de comandos
        CooldownConfig cooldownConfig = new CooldownConfig(plugin); // Passando o plugin para CooldownConfig

        // Verifique se o comando existe antes de definir o executor
        if (plugin.getCommand("fly") != null) {
            plugin.getCommand("fly").setExecutor(new FlyCommand());
        } else {
            plugin.getLogger().warning("Comando /fly não encontrado no plugin.yml.");
        }

        if (plugin.getCommand("vanish") != null) {
            plugin.getCommand("vanish").setExecutor(new VanishCommand());
        } else {
            plugin.getLogger().warning("Comando /vanish não encontrado no plugin.yml.");
        }

        if (plugin.getCommand("v") != null) {
            plugin.getCommand("v").setExecutor(new VanishCommand());
        } else {
            plugin.getLogger().warning("Comando /v não encontrado no plugin.yml.");
        }

        if (plugin.getCommand("ci") != null) {
            plugin.getCommand("ci").setExecutor(new CiCommand());
        } else {
            plugin.getLogger().warning("Comando /ci não encontrado no plugin.yml.");
        }

        if (plugin.getCommand("everyone") != null) {
            plugin.getCommand("everyone").setExecutor(new EveryoneCommand());
        } else {
            plugin.getLogger().warning("Comando /everyone não encontrado no plugin.yml.");
        }

        // Passando CooldownConfig como argumento para FeedCommand e HealCommand
        if (plugin.getCommand("feed") != null) {
            plugin.getCommand("feed").setExecutor(new FeedCommand(cooldownConfig));
        } else {
            plugin.getLogger().warning("Comando /feed não encontrado no plugin.yml.");
        }

        if (plugin.getCommand("heal") != null) {
            plugin.getCommand("heal").setExecutor(new HealCommand(cooldownConfig));
        } else {
            plugin.getLogger().warning("Comando /heal não encontrado no plugin.yml.");
        }
    }

    public static void unregisterCommand(JavaPlugin plugin, String commandName) {
        try {
            // Usar reflexão para acessar o CommandMap
            Object server = Bukkit.getServer();
            Field commandMapField = server.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(server);

            // Usar reflexão para acessar o campo "commands" do CommandMap
            Field commandsField = commandMap.getClass().getDeclaredField("commands");
            commandsField.setAccessible(true);
            java.util.Map<String, Command> commands = (java.util.Map<String, Command>) commandsField.get(commandMap);

            // Remover o comando, se ele existir
            if (commands.containsKey(commandName)) {
                commands.remove(commandName);
                plugin.getLogger().info("Comando " + commandName + " desregistrado com sucesso.");
            } else {
                plugin.getLogger().warning("Comando " + commandName + " não encontrado.");
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Erro ao desregistrar comando: " + commandName);
            e.printStackTrace();
        }
    }
}
