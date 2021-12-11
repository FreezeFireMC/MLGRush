package de.chaos.mc.server.mlgrush;

import de.chaos.mc.server.mlgrush.utils.gamutils.GameState;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import lombok.Data;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Data
public class GameStatus {
    private GameState gameState;
    private HashMap<UUID, Player> spectatorPlayers = new HashMap<>();
    private HashMap<UUID, GamePlayer> onlinePlayers = new HashMap<>();
    private MapObject gameMap;
}
