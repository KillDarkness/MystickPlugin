package mysticmc.mystick;

import mysticmc.mystick.handler.CommandsHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Mystick plugin habilitado!");
        CommandsHandler.registerCommands(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Mystick plugin desabilitado!");
    }
}
