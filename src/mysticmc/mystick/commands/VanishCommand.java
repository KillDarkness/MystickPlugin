package mysticmc.mystick.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class VanishCommand implements CommandExecutor {

    private final Set<Player> vanishedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("§cSomente jogadores podem usar este comando diretamente.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            if (!player.hasPermission("mystick.vanish")) {
                player.sendMessage("§cVocê não tem permissão para usar este comando.");
                return true;
            }

            toggleVanish(player, player);
            return true;
        }

        if (args.length == 1) {
            if (!player.hasPermission("mystick.*")) {
                player.sendMessage("§cVocê não tem permissão para ver quem está no vanish.");
                return true;
            }

            if ("list".equalsIgnoreCase(args[0])) {
                if (vanishedPlayers.isEmpty()) {
                    player.sendMessage("§eNão há jogadores no vanish no momento.");
                } else {
                    player.sendMessage("§eJogadores no vanish:");
                    vanishedPlayers.forEach(p -> player.sendMessage("§6- §a" + p.getName()));
                }
                return true;
            }
        }

        player.sendMessage("§cUso incorreto. Tente: /vanish [list]");
        return true;
    }

    private void toggleVanish(Player executor, Player target) {
        boolean newState = !vanishedPlayers.contains(target);

        if (newState) {
            vanishedPlayers.add(target);
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (!p.hasPermission("mystick.*")) {
                    p.hidePlayer(target);
                }
            });

            target.sendMessage("§b§lMystick §6§l》§aVocê está no modo vanish!");
            target.sendTitle("§aVanish Ativado", "§eJogadores não podem te ver!", 10, 50, 10);
        } else {
            vanishedPlayers.remove(target);
            Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(target));

            target.sendMessage("§b§lMystick §6§l》§cVocê saiu do modo vanish!");
            target.sendTitle("§cVanish Desativado", "§eVocê está visível novamente!", 10, 50, 10);
        }

        if (executor != target) {
            executor.sendMessage("§b§lMystick §6§l》§aO vanish de §e" + target.getName() + " §afoi " + (newState ? "ativado!" : "desativado!") + ".");
        }
    }
}
