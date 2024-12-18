package mysticmc.mystick;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Mystick plugin habilitado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Mystick plugin desabilitado!");
    }
}
