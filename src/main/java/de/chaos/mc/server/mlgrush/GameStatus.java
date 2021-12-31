package de.chaos.mc.server.mlgrush;

import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameState;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import lombok.Data;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Data
public class GameStatus {
    private GameState gameState;
    private HashMap<UUID, GamePlayer> spectatorPlayers = new HashMap<>();
    private HashMap<UUID, GamePlayer> onlinePlayers = new HashMap<>();
    private ArrayList<Block> sandStoneBlocks = new ArrayList<>();
    private MapObject gameMap;
    private int teambluePoints;
    private int teamredPoints;
}
