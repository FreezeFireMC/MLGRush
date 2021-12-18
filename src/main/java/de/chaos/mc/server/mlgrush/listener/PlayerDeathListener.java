package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameTeam;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.MLGRushProfileInv;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import de.chaos.mc.serverapi.utils.stringLibary.DefaultMessages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class PlayerDeathListener implements Listener {
    private StatsInterface statsInterface;
    private MLGRushProfileInv profileInv;
    public PlayerDeathListener(StatsInterface statsInterface, MLGRushProfileInv mlgRushProfileInv) {
        this.statsInterface = statsInterface;
        this.profileInv = mlgRushProfileInv;
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();
        statsInterface.addDeaths(uuid, 1);
        Player killer = Bukkit.getPlayer( MLGRush.getGameStatus().getOnlinePlayers().get(0).getUuid());
        if (killer == player) {
            killer = Bukkit.getPlayer( MLGRush.getGameStatus().getOnlinePlayers().get(1).getUuid());
        }
            player.sendMessage(DefaultMessages.normalMessage("Du wurdest von " + killer.getName() + " getötet!"));
            killer.sendMessage(DefaultMessages.normalMessage("Du hast " + killer.getName() + " getötet!"));
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        statsInterface.addKills(killer.getUniqueId(), 1);
        event.getDrops().clear();
        player.spigot().respawn();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        GameStatus status = MLGRush.getGameStatus();
        if (status.getOnlinePlayers().get(event.getPlayer().getUniqueId()).getGameTeam().equals(GameTeam.RED)) {
            event.setRespawnLocation(MLGRush.getGameStatus().getGameMap().getRedSpawn());
        } else {
            event.setRespawnLocation(MLGRush.getGameStatus().getGameMap().getBlueSpawn());
        }
        profileInv.setInventory(event.getPlayer());
    }
}
