package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.serverapi.utils.stringLibary.DefaultMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(DefaultMessages.joinMessage(player));

        switch (MLGRush.getGameStatus().getOnlinePlayers().size()) {
            case 1:
                player.teleport(MLGRush.getGameStatus().getGameMap().getRedSpawn());
                GamePlayer gamePlayer = GamePlayer.builder()
                        .uuid(player.getUniqueId())
                        .kills(0)
                        .deaths(0)
                        .build();
                MLGRush.getGameStatus().getOnlinePlayers().put(player.getUniqueId(), gamePlayer);
                break;
            case 2:
                player.teleport(MLGRush.getGameStatus().getGameMap().getBlueSpawn());
                GamePlayer gamePlayer1 = GamePlayer.builder()
                        .uuid(player.getUniqueId())
                        .kills(0)
                        .deaths(0)
                        .build();
                MLGRush.getGameStatus().getOnlinePlayers().put(player.getUniqueId(), gamePlayer1);
                break;
            default:
                player.teleport(MLGRush.getGameStatus().getGameMap().getSpawnSpectator());
                MLGRush.getGameStatus().getSpectatorPlayers().put(player.getUniqueId(), player);
                break;

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (MLGRush.getGameStatus().getOnlinePlayers().containsKey(event.getPlayer())) {
            MLGRush.getGameController().endGame();
        }
    }
}
