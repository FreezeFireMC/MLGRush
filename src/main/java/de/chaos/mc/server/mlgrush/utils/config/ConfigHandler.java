package de.chaos.mc.server.mlgrush.utils.config;


import de.chaos.mc.server.mlgrush.utils.config.arenaConfig.ArenaConfigHandler;
import de.chaos.mc.server.mlgrush.utils.config.generalconfig.GeneralConfig;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigHandler {
    private ArenaConfigHandler arenaConfigHandler;
    private GeneralConfig generalConfig;
    private Plugin plugin;
    public ConfigHandler (ArenaConfigHandler arenaConfigHandler, GeneralConfig generalConfig, Plugin plugin) {
        this.arenaConfigHandler = arenaConfigHandler;
        this.plugin = plugin;
        this.generalConfig = generalConfig;
        loadConfigs();
    }

    public MapObject getMapObject = null;
    private void loadConfigs() {
        File path = new File(plugin.getDataFolder().getPath());
        if (!path.exists()) {
            path.mkdir();
        }

        arenaConfigHandler.loadConfig();
        generalConfig.loadConfig();
        try {
            if (arenaConfigHandler.readMapObject().equals(null)) return;
        } catch (NullPointerException nullPointerException) {
            return;
        }
        getMapObject = arenaConfigHandler.readMapObject();
    }

}
