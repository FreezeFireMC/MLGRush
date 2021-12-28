package de.chaos.mc.server.mlgrush.utils.config.arenaConfig;

import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class ArenaConfigHandler {

    @Getter private File arenaFile;
    @Getter private YamlConfiguration arenaConfig;

    public void loadConfig() {
        arenaFile = new File(MLGRush.getInstance().getDataFolder(), "arena.yml");
        arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
        saveArenaConfig();
    }


    public void saveMap(MapObject mapObject) {
        arenaConfig.set("MapName", mapObject.getMapName());
        arenaConfig.set("BlueBed", mapObject.getBlueBed());
        arenaConfig.set("BlueSpawn", mapObject.getBlueSpawn());
        arenaConfig.set("RedBed", mapObject.getRedBed());
        arenaConfig.set("RedSpawn", mapObject.getRedSpawn());
        arenaConfig.set("SpectatorSpawn", mapObject.getSpawnSpectator());
        arenaConfig.set("DeathYCordination", mapObject.getDeathYCordinate());
        saveArenaConfig();
    }

    public MapObject readMapObject() {
        if (arenaFile.length() == 0) {
            return null;
        }

        MapObject mapObject = MapObject.builder()
                .mapName(arenaConfig.get("MapName").toString())
                .blueBed(arenaConfig.getSerializable("BlueBed", Location.class))
                .blueSpawn(arenaConfig.getSerializable("BlueSpawn", Location.class))
                .redBed( arenaConfig.getSerializable("RedBed", Location.class))
                .redSpawn(arenaConfig.getSerializable("RedSpawn", Location.class))
                .spawnSpectator(arenaConfig.getSerializable("SpectatorSpawn", Location.class))
                .deathYCordinate(arenaConfig.getInt("DeathYCordination"))
                .build();
        return mapObject;
    }

    public void saveArenaConfig() {
        try {
            if (!arenaFile.exists()) arenaFile.createNewFile();
            arenaConfig.save(arenaFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}