package de.chaos.mc.server.mlgrush.utils.statsLibary;

import java.util.UUID;

public interface StatsInterface {
    public void addKills(UUID uuid, long amount);
    public void addDeaths(UUID uuid, long amount);
    public void addBrokenBeads(UUID uuid, long amount);
    public long getKills(UUID uuid);
    public long getDeaths(UUID uuid);
    public long getBrokenBed(UUID uuid);
}
