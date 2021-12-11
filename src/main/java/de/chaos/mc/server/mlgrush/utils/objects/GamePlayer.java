package de.chaos.mc.server.mlgrush.utils.objects;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GamePlayer {
    public UUID uuid;
    public int kills;
    public int deaths;
}
