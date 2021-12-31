package de.chaos.mc.server.mlgrush.utils.gamutils;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.MLGRushPlayerLanguage;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameState;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameStateSwitcher;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.servicegroup.ICloudServiceGroup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;
import java.util.UUID;

public class GameController {
    private GameStateSwitcher gameStateSwitcher;
    private StatsInterface statsInterface;
    public GameController(GameStateSwitcher gameStateSwitcher, StatsInterface statsInterface) {
        this.gameStateSwitcher = gameStateSwitcher;
        this.statsInterface = statsInterface;
    }



    public void bedBreak(Player blockBreakPlayer, Location bedloc, BlockBreakEvent event) {
        MLGRushPlayerLanguage playerLanguage = MLGRush.getOnlinePlayers().get(blockBreakPlayer.getUniqueId());
        UUID blockBreakUUID = blockBreakPlayer.getUniqueId();
        GameStatus gameStatus = MLGRush.getGameStatus();
        if (gameStatus.getGameMap().getBlueBed().distance(bedloc) <= 2) {
            if (gameStatus.getOnlinePlayers().get(blockBreakUUID).getGameTeam().equals(GameTeam.RED)) {
                gameStatus.setTeamredPoints(gameStatus.getTeamredPoints() + 1);
                for (UUID uuid : gameStatus.getOnlinePlayers().keySet()) {
                    GamePlayer gamePlayer = gameStatus.getOnlinePlayers().get(uuid);
                    if (gamePlayer.getGameTeam().equals(GameTeam.BLUE)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getBlueSpawn());
                        for (UUID uuid1 : gameStatus.getOnlinePlayers().keySet()) {
                            if (gameStatus.getOnlinePlayers().get(uuid1).getGameTeam().equals(GameTeam.RED)) {
                                Bukkit.getPlayer(uuid1).teleport(gameStatus.getGameMap().getRedSpawn());
                            }
                        }
                    }
                    if (gamePlayer.getGameTeam().equals(GameTeam.RED)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getRedSpawn());
                        for (UUID uuid1 : gameStatus.getOnlinePlayers().keySet()) {
                            if (gameStatus.getOnlinePlayers().get(uuid1).getGameTeam().equals(GameTeam.BLUE)) {
                                Bukkit.getPlayer(uuid1).teleport(gameStatus.getGameMap().getBlueSpawn());
                            }
                        }
                    }
                }
                event.setCancelled(true);
                statsInterface.addBrokenBeads(blockBreakUUID, 1);
                clearPlacedBlocks();

                if (gameStatus.getTeamredPoints() == 10) {
                    for (UUID uuid : gameStatus.getOnlinePlayers().keySet()) {
                        MLGRushPlayerLanguage gameLanguage = MLGRush.getOnlinePlayers().get(uuid);
                        Bukkit.getPlayer(uuid).sendMessage(gameLanguage.playerWon(uuid, blockBreakPlayer));
                        this.endGame();
                    }
                }
                return;
            } else {
                blockBreakPlayer.sendMessage(playerLanguage.getCantDestroyYouBed());
                event.setCancelled(true);
            }
        }

        if (gameStatus.getGameMap().getRedBed().distance(bedloc) <= 2) {
            if (gameStatus.getOnlinePlayers().get(blockBreakUUID).getGameTeam().equals(GameTeam.BLUE)) {
                gameStatus.setTeambluePoints(gameStatus.getTeambluePoints() + 1);
                for (UUID uuid : gameStatus.getOnlinePlayers().keySet()) {
                    GamePlayer gamePlayer = gameStatus.getOnlinePlayers().get(uuid);
                    if (gamePlayer.getGameTeam().equals(GameTeam.BLUE)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getBlueSpawn());
                        for (UUID uuid1 : gameStatus.getOnlinePlayers().keySet()) {
                            if (gameStatus.getOnlinePlayers().get(uuid1).getGameTeam().equals(GameTeam.RED)) {
                                Bukkit.getPlayer(uuid1).teleport(gameStatus.getGameMap().getRedSpawn());
                            }
                        }
                    }
                    if (gamePlayer.getGameTeam().equals(GameTeam.RED)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getRedSpawn());
                        for (UUID uuid1 : gameStatus.getOnlinePlayers().keySet()) {
                            if (gameStatus.getOnlinePlayers().get(uuid1).getGameTeam().equals(GameTeam.BLUE)) {
                                Bukkit.getPlayer(uuid1).teleport(gameStatus.getGameMap().getBlueSpawn());
                            }
                        }
                    }
                }
                this.clearPlacedBlocks();
                event.setCancelled(true);
                statsInterface.addBrokenBeads(blockBreakUUID, 1);
                if (gameStatus.getTeambluePoints() == 10) {
                    for (UUID uuid : gameStatus.getOnlinePlayers().keySet()) {
                        MLGRushPlayerLanguage gameLanguage = MLGRush.getOnlinePlayers().get(uuid);
                        Bukkit.getPlayer(uuid).sendMessage(gameLanguage.playerWon(uuid, blockBreakPlayer));
                        this.endGame();
                    }
                }
                return;
            } else {
                blockBreakPlayer.sendMessage(playerLanguage.getCantDestroyYouBed());
                event.setCancelled(true);
            }
        }
    }




    public void clearPlacedBlocks() {
        for (Block block : MLGRush.getGameStatus().getSandStoneBlocks()) {
            block.setType(Material.AIR, true);
        }
    }

    public void endGame() {
        gameStateSwitcher.switchGamestate(GameState.ENDING);
        ICloudServiceGroup serviceTask = CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName("MLGRushHub");
        ICloudService service = null;
        do {
            service = serviceTask.getAllServices().get(new Random().nextInt(serviceTask.getAllServices().size()));
        } while (service == null || service.isFull());

        for (Player player : Bukkit.getOnlinePlayers()) {
            assert serviceTask != null;
            ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId());
            CloudAPI.getInstance().getCloudPlayerManager().connectPlayer(cloudPlayer, service);
        }
        Bukkit.getServer().shutdown();
    }
}
