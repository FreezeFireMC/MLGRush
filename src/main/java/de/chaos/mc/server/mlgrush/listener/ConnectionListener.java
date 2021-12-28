package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.MLGRushStringLoader;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameTeam;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameState;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.MLGRushProfileInv;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    private MLGRushProfileInv mlgRushProfileInv;
    public ConnectionListener(MLGRushProfileInv mlgRushProfileInv) {
        this.mlgRushProfileInv = mlgRushProfileInv;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(AbstractMessages.joinMessage(player));
        MLGRushStringLoader.loadLanguage(player.getUniqueId());

        switch (MLGRush.getGameStatus().getOnlinePlayers().size()) {
            case 0:
                player.teleport(MLGRush.getGameStatus().getGameMap().getRedSpawn());
                GamePlayer gamePlayer = GamePlayer.builder()
                        .uuid(player.getUniqueId())
                        .points(0)
                        .gameTeam(GameTeam.RED)
                        .build();
                MLGRush.getGameStatus().getOnlinePlayers().put(player.getUniqueId(), gamePlayer);
                mlgRushProfileInv.setInventory(player);
                break;
            case 1:
                player.teleport(MLGRush.getGameStatus().getGameMap().getBlueSpawn());
                GamePlayer gamePlayer1 = GamePlayer.builder()
                        .uuid(player.getUniqueId())
                        .points(0)
                        .gameTeam(GameTeam.BLUE)
                        .build();
                MLGRush.getGameStatus().getOnlinePlayers().put(player.getUniqueId(), gamePlayer1);
                mlgRushProfileInv.setInventory(player);
                MLGRush.getGameStateSwitcher().switchGamestate(GameState.INGAME);
                break;
            default:
                player.teleport(MLGRush.getGameStatus().getGameMap().getSpawnSpectator());
                GamePlayer spectator = GamePlayer.builder()
                        .uuid(player.getUniqueId())
                        .points(0)
                        .gameTeam(GameTeam.SPECTATOR)
                        .build();
                MLGRush.getGameStatus().getSpectatorPlayers().put(player.getUniqueId(), spectator);
                break;

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (MLGRush.getGameStatus().getOnlinePlayers().containsKey(event.getPlayer().getUniqueId())) {
            MLGRush.getGameController().endGame();
        } else {
            if (MLGRush.getGameStatus().getSpectatorPlayers().containsKey(event.getPlayer().getUniqueId())) {
                MLGRush.getGameStatus().getSpectatorPlayers().remove(event.getPlayer().getUniqueId());
            }
        }
    }
}
