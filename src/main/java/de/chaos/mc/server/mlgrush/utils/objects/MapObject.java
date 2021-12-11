package de.chaos.mc.server.mlgrush.utils.objects;

import lombok.Builder;
import lombok.Data;
import org.bukkit.Location;

@Data
@Builder
public class MapObject {
    public String mapName;
    public Location redSpawn;
    public Location redBed;
    public Location blueSpawn;
    public Location blueBed;
    public Location spawnSpectator;
    public Location endLocation;
    public int deathYCordinate;
}