package de.chaos.mc.server.mlgrush.utils.gamutils;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.MLGRushPlayerLanguage;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameState;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameStateSwitcher;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

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
                gameStatus.getOnlinePlayers().get(blockBreakUUID).setPoints(gameStatus.getOnlinePlayers().get(blockBreakUUID).getPoints() + 1);
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

                if (gameStatus.getOnlinePlayers().get(blockBreakPlayer.getUniqueId()).getPoints() == 10) {
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
                gameStatus.getOnlinePlayers().get(blockBreakUUID).setPoints(gameStatus.getOnlinePlayers().get(blockBreakUUID).getPoints() + 1);
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
                if (gameStatus.getOnlinePlayers().get(blockBreakPlayer.getUniqueId()).getPoints() == 10) {
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
        ServiceTask serviceTask = CloudNetDriver.getInstance().getServiceTaskProvider().getServiceTask("MLGRushHub");
        for (Player player : Bukkit.getOnlinePlayers()) {
            assert serviceTask != null;
            CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getPlayerExecutor(player.getUniqueId()).connectToTask(serviceTask.getName(), ServerSelectorType.LOWEST_PLAYERS);
        }
        Bukkit.getServer().shutdown();
    }
}
