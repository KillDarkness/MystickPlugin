package mysticmc.mystick.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("§cSomente jogadores podem usar este comando diretamente.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            if (!player.hasPermission("mystick.fly")) {
                player.sendMessage("§cVocê não tem permissão para usar este comando.");
                return true;
            }

            toggleFly(player, player);
            return true;
        }

        if (args.length == 1) {
            if (!player.hasPermission("mystick.*")) {
                player.sendMessage("§cVocê não tem permissão para alterar o fly de outros jogadores.");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cJogador não encontrado.");
                return true;
            }

            toggleFly(player, target);
            return true;
        }

        player.sendMessage("§cUso incorreto. Tente: /fly [jogador]");
        return true;
    }

    private void toggleFly(Player executor, Player target) {
        boolean newState = !target.getAllowFlight();
        target.setAllowFlight(newState);

        String message = "§b§lMystick §6§l》§aSeu fly foi " + (newState ? "ativado!" : "desativado!");
        target.sendMessage(message);

        if (executor != target) {
            executor.sendMessage("§b§lMystick §6§l》§aO fly de §e" + target.getName() + " §afoi " + (newState ? "ativado!" : "desativado!") + ".");
        }

        if (newState) {
            target.sendTitle("§aFly Ativado", "§eAproveite!", 10, 50, 10);
        } else {
            target.sendTitle("§cFly Desativado", "§eAté a próxima!", 10, 50, 10);
        }
    }
}
