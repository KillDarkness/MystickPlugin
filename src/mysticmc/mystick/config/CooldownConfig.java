package mysticmc.mystick.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CooldownConfig {

    private final File file;
    private final YamlConfiguration config;

    public CooldownConfig(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "cooldown.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        createDefaultConfig();
    }

    private void createDefaultConfig() {
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("# Configuração de cooldown para comandos\n");
                writer.write("# Tempo em minutos\n");
                writer.write("heal.cooldown: 5\n");
                writer.write("feed.cooldown: 3\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getCooldown(String command) {
        return config.getInt(command + ".cooldown", 0);
    }
}
