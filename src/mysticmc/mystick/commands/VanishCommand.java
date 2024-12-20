package mysticmc.mystick.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class VanishCommand implements CommandExecutor {

    private final Set<Player> vanishedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // Verifica se o remetente é um jogador
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cSomente jogadores podem ativar o vanish para si mesmos.");
                return true;
            }

            Player player = (Player) sender;
            if (!player.hasPermission("mystick.vanish")) {
                player.sendMessage("§cVocê não tem permissão para usar este comando.");
                return true;
            }

            toggleVanish(player, player);
            return true;
        }

        if (args.length == 1) {
            if ("list".equalsIgnoreCase(args[0])) {
                if (vanishedPlayers.isEmpty()) {
                    sender.sendMessage("§eNão há jogadores no vanish no momento.");
                } else {
                    sender.sendMessage("§eJogadores no vanish:");
                    vanishedPlayers.forEach(p -> sender.sendMessage("§6- §a" + p.getName()));
                }
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cJogador não encontrado.");
                return true;
            }

            if (!sender.hasPermission("mystick.*")) {
                sender.sendMessage("§cVocê não tem permissão para gerenciar o vanish de outros jogadores.");
                return true;
            }

            toggleVanish(sender, target);
            return true;
        }

        sender.sendMessage("§cUso incorreto. Tente: /vanish [jogador|list]");
        return true;
    }

    private void toggleVanish(CommandSender executor, Player target) {
        boolean newState = !vanishedPlayers.contains(target);

        if (newState) {
            vanishedPlayers.add(target);
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (!p.hasPermission("mystick.*")) {
                    p.hidePlayer(JavaPlugin.getProvidingPlugin(getClass()), target);
                }
            });

            target.sendMessage("§b§lMystick §6§l》§aVocê está no modo vanish!");
            sendActionBar(target, "§aVocê Está no modo &c&lVanish! &aOs jogadores não podem te ver.");
        } else {
            vanishedPlayers.remove(target);
            Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(JavaPlugin.getProvidingPlugin(getClass()), target));

            target.sendMessage("§b§lMystick §6§l》§cVocê saiu do modo vanish!");
            sendActionBar(target, "§cVocê está visível novamente!");
        }

        if (executor instanceof Player && executor != target) {
            executor.sendMessage("§b§lMystick §6§l》§aO vanish de §e" + target.getName() + " §afoi " + (newState ? "ativado!" : "desativado!") + ".");
        } else if (!(executor instanceof Player)) {
            executor.sendMessage("§b§lMystick §6§l》§aO vanish de §e" + target.getName() + " §afoi " + (newState ? "ativado!" : "desativado!") + ".");
        }
    }

    private void sendActionBar(Player player, String message) {
        player.sendMessage(message); // Método alternativo em caso de suporte limitado
    }
}
