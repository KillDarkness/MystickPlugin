package mysticmc.mystick.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiscordManager {

    private final Plugin plugin;
    private JDA jda;

    public DiscordManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        createDiscordConfigFile(); // Cria o arquivo de configuração se não existir
        startBot(); // Inicia o bot após a configuração
    }

    private void createDiscordConfigFile() {
        File configFile = new File(plugin.getDataFolder(), "discord.yml");
        
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write("# |=======================================|\n");
                writer.write("# |                                       |\n");
                writer.write("# |          MYSTICK PLUGIN               |\n");
                writer.write("# |     Arquivo de Configuração           |\n");
                writer.write("# |             Discord                   |\n");
                writer.write("# |                                       |\n");
                writer.write("# |=======================================|\n\n");
                writer.write("# Passos para configurar o bot:\n\n");
                writer.write("# 1. Vá em https://discord.com/developers/applications\n");
                writer.write("# 2. Clique em \"New Application\" e crie sua aplicação.\n");
                writer.write("# 3. No menu lateral, clique em \"BOT\" e crie o bot.\n");
                writer.write("# 4. Desça até as \"Privileged Gateway Intents\" e ative as três:\n");
                writer.write("#    - Presence Intent\n");
                writer.write("#    - Server Members Intent\n");
                writer.write("#    - Message Content Intent\n");
                writer.write("# 5. Volte ao topo e clique em \"Reset Token\".\n");
                writer.write("# 6. Copie o token gerado e cole abaixo.\n\n");
                writer.write("# Token do bot Discord:\n");
                writer.write("token: \"coloque-seu-token-aqui\"\n\n");
                writer.write("# Prefixo padrão para comandos no Discord (opcional):\n");
                writer.write("# Deixe em branco se você não usar comandos prefixados por enquanto.\n");
                writer.write("prefix: \"!\"\n\n");
                writer.write("# Logs:\n");
                writer.write("# Ative ou desative logs do bot no console do servidor.\n");
                writer.write("logs: true\n");

                plugin.getLogger().info("Arquivo discord.yml criado com sucesso! Configure-o antes de iniciar o plugin.");
            } catch (IOException e) {
                plugin.getLogger().severe("Erro ao criar o arquivo discord.yml: " + e.getMessage());
            }
        }
    }

    private void startBot() {
        try {
            // Localizar o arquivo discord.yml
            File configFile = new File(plugin.getDataFolder(), "discord.yml");

            // Carregar as configurações
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            String token = config.getString("token");

            if (token == null || token.isEmpty() || token.equals("coloque-seu-token-aqui")) {
                plugin.getLogger().severe("Token do Discord não configurado no arquivo discord.yml!");
                return;
            }

            // Inicializar o JDA
            jda = JDABuilder.createDefault(token).build();
            plugin.getLogger().info("Bot do Discord iniciado com sucesso!");
        } catch (Exception e) {
            plugin.getLogger().severe("Erro ao iniciar o bot do Discord.");
            e.printStackTrace();
        }
    }

    public void stopBot() {
        if (jda != null) {
            jda.shutdown();
            plugin.getLogger().info("Bot do Discord desligado.");
        }
    }
}
