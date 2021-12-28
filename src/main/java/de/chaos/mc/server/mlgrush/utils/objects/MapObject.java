package de.chaos.mc.server.mlgrush.utils.objects;

import lombok.*;
import org.bukkit.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MapObject  {
    public String mapName;
    public Location redSpawn;
    public Location redBed;
    public Location blueSpawn;
    public Location blueBed;
    public Location spawnSpectator;
    public int deathYCordinate;
}