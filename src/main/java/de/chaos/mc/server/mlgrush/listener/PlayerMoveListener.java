package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameTeam;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.MLGRushProfileInv;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class PlayerMoveListener implements Listener {
    private StatsInterface statsInterface;
    private MLGRushProfileInv profileInv;
    public PlayerMoveListener(StatsInterface statsInterface, MLGRushProfileInv mlgRushProfileInv) {
        this.statsInterface = statsInterface;
        this.profileInv = mlgRushProfileInv;
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        GameStatus status = MLGRush.getGameStatus();
        if (player.getLocation().getY() <= MLGRush.getGameStatus().getGameMap().getDeathYCordinate()) {
            UUID uuid = player.getUniqueId();
            Player killer = null;

            for (UUID killerid : status.getOnlinePlayers().keySet()) {
                if (killerid != uuid) {
                killer = Bukkit.getPlayer(killerid);
                }
            }
            player.sendMessage(AbstractMessages.normalMessage("Du wurdest von " + killer.getName() + " getötet!"));
            killer.sendMessage(AbstractMessages.normalMessage("Du hast " + player.getName() + " getötet!"));
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);


            if (status.getOnlinePlayers().get(event.getPlayer().getUniqueId()).getGameTeam().equals(GameTeam.RED)) {
                player.teleport(MLGRush.getGameStatus().getGameMap().getRedSpawn());
                statsInterface.addDeaths(killer.getUniqueId(), 1);
            } else {
                player.teleport(MLGRush.getGameStatus().getGameMap().getBlueSpawn());
                statsInterface.addKills(killer.getUniqueId(), 1);
            }
            profileInv.setInventory(event.getPlayer());
            statsInterface.addDeaths(player.getUniqueId(), 1);
        }
    }
}
