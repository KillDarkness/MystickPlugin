package mysticmc.mystick;

import mysticmc.mystick.config.CooldownConfig;
import mysticmc.mystick.handler.CommandsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private CooldownConfig cooldownConfig;

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
        // Registrar comandos
        
        CommandsHandler.registerCommands(this);
    }

    @Override
    public void onDisable() {
        // Mensagem de desativação
        Bukkit.getLogger().info("§b[Mystick] §cPlugin desativado!");
    }

    public CooldownConfig getCooldownConfig() {
        return cooldownConfig;
    }
}
