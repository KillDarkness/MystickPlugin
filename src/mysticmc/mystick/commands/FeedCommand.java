package mysticmc.mystick.commands;

import mysticmc.mystick.config.CooldownConfig;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class FeedCommand implements CommandExecutor {

    private final CooldownConfig cooldownConfig;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public FeedCommand(CooldownConfig cooldownConfig) {
        this.cooldownConfig = cooldownConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;

        // Verificar permissões
        if (!player.hasPermission("mystick.feed") && !player.hasPermission("mystick.*")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        // Definir alvo
        Player target = player;
        if (args.length > 0 && player.hasPermission("mystick.*")) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cJogador não encontrado.");
                return true;
            }
        }

        // Verificar cooldown
        if (!player.hasPermission("mystick.*")) {
            int cooldownMinutes = cooldownConfig.getCooldown("feed");
            long cooldownTime = cooldownMinutes * 60 * 1000L;
            long lastUsed = cooldowns.getOrDefault(player.getUniqueId(), 0L);
            long timeLeft = (lastUsed + cooldownTime) - System.currentTimeMillis();

            if (timeLeft > 0) {
                player.sendMessage("§cVocê deve esperar " + (timeLeft / 1000) + " segundos antes de usar este comando novamente.");
                return true;
            }
        }

        // Alimentar
        target.setFoodLevel(20);
        target.setSaturation(20);
        target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);

        // Exibir mensagens
        if (target.equals(player)) {
            player.sendMessage("§b§lMystick §6§l》§aSua fome foi restaurada!");
        } else {
            player.sendMessage("§b§lMystick §6§l》§aVocê restaurou a fome de §e" + target.getName() + "§a!");
            target.sendMessage("§b§lMystick §6§l》§aSua fome foi restaurada por §e" + player.getName() + "§a!");
        }

        // Registrar cooldown
        if (!player.hasPermission("mystick.*")) {
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        }

        return true;
    }
}
