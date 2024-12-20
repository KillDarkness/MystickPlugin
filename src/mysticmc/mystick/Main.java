package mysticmc.mystick;

import mysticmc.mystick.config.CooldownConfig;
import mysticmc.mystick.discord.DiscordManager;
import mysticmc.mystick.handler.CommandsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private CooldownConfig cooldownConfig;
    private DiscordManager discordManager;

    @Override
    public void onEnable() {
        // Mensagem de ativação
        Bukkit.getLogger().info("§b[Mystick] §aPlugin ativado!");

        // Criar diretório de dados, se necessário
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Inicializar configuração de cooldown
        cooldownConfig = new CooldownConfig(this);

        // Inicializar bot do Discord
        discordManager = new DiscordManager(this);
        discordManager.initialize(); // Método que cuida da criação e inicialização do bot

        // Registrar comandos
        CommandsHandler.registerCommands(this);
    }

    @Override
    public void onDisable() {
        // Desligar bot do Discord
        if (discordManager != null) {
            discordManager.stopBot();
        }

        // Mensagem de desativação
        Bukkit.getLogger().info("§b[Mystick] §cPlugin desativado!");
    }

    public CooldownConfig getCooldownConfig() {
        return cooldownConfig;
    }
}
