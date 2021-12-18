package de.chaos.mc.server.mlgrush.utils.objects;

import de.chaos.mc.server.mlgrush.utils.gamutils.GameTeam;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GamePlayer {
    public UUID uuid;
    public int points;
    public GameTeam gameTeam;
}
