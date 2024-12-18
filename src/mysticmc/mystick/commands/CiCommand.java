package mysticmc.mystick.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("§cSomente jogadores podem usar este comando diretamente.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (!player.hasPermission("mystick.ci.me")) {
                player.sendMessage("§cVocê não tem permissão para limpar seu próprio inventário.");
                return true;
            }

            clearInventory(player, player);
            return true;
        }

        if (args.length == 1) {
            if (!player.hasPermission("mystick.ci.all")) {
                player.sendMessage("§cVocê não tem permissão para limpar o inventário de outros jogadores.");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cJogador não encontrado.");
                return true;
            }

            clearInventory(player, target);
            return true;
        }

        player.sendMessage("§cUso incorreto. Tente: /ci [jogador]");
        return true;
    }

    private void clearInventory(Player executor, Player target) {
        target.getInventory().clear();
        target.sendMessage("§b§lMystick §6§l》§aSeu inventário foi limpo!");
        
        if (executor != target) {
            executor.sendMessage("§b§lMystick §6§l》§aO inventário de §e" + target.getName() + " §afoi limpo!");
        }
    }
}
