package mysticmc.mystick.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EveryoneCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mystick.everyone") && !sender.hasPermission("mystick.*")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUso incorreto. Tente: /everyone <mensagem>");
            return true;
        }

        String message = String.join(" ", args);

        // Limpar o chat de todos os jogadores
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 100; i++) {
                player.sendMessage("");
            }
        }

        // Mensagem de aviso no chat
        String chatMessage = "§c§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n" +
                             "§c§lAVISO IMPORTANTE!\n" +
                             "§f§l" + message + "\n" +
                             "§c§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";

        Bukkit.broadcastMessage(chatMessage);

        // Título vermelho para todos os jogadores
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle("§c§lAVISO IMPORTANTE!", "§f§l" + message, 10, 100, 10);
        }

        sender.sendMessage("§aAviso enviado com sucesso!");
        return true;
    }
}
