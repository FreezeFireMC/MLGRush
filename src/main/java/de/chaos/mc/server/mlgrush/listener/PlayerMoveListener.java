package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.MLGRush;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getY() <= MLGRush.getGameStatus().getGameMap().getDeathYCordinate()) {
            player.damage(20);
        }
    }
}
