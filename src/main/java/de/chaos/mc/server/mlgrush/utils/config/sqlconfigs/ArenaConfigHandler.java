package de.chaos.mc.server.mlgrush.utils.config.sqlconfigs;

import com.google.gson.Gson;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import lombok.Getter;

import java.io.*;

@Getter
public class ArenaConfigHandler {

    private Gson gson = new Gson();
    private final File path = MLGRush.getInstance().getDataFolder();
    private final File arenaFile = new File(path, "Arena.json");

    public void saveMap(MapObject mapObject) {
        String toFile = gson.toJson(mapObject);
        try (FileWriter stringWriter = new FileWriter(arenaFile)) {
            if (!arenaFile.exists()) arenaFile.createNewFile();
            stringWriter.write(toFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public MapObject readMapObject() {
        if (!arenaFile.exists()) {
            try {
                arenaFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            MapObject mapObject = gson.fromJson(new FileReader(arenaFile), MapObject.class);
            return mapObject;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}