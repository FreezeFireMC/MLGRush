package de.chaos.mc.server.mlgrush.utils.config;


import de.chaos.mc.server.mlgrush.utils.config.sqlconfigs.ArenaConfigHandler;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigHandler {
    private ArenaConfigHandler arenaConfigHandler;
    private Plugin plugin;
    public ConfigHandler (ArenaConfigHandler arenaConfigHandler, Plugin plugin) {
        this.arenaConfigHandler = arenaConfigHandler;
        this.plugin = plugin;
        loadConfigs();
    }

    public MapObject getMapObject = null;
    private void loadConfigs() {
        File path = plugin.getDataFolder();
        if (!path.exists()) {
            path.mkdir();
        }

        MapObject mapObject = arenaConfigHandler.readMapObject();
        getMapObject = mapObject;
    }

}
