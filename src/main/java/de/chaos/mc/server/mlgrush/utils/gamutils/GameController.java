package de.chaos.mc.server.mlgrush.utils.gamutils;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameState;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameStateSwitcher;
import de.chaos.mc.server.mlgrush.utils.objects.GamePlayer;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import de.chaos.mc.serverapi.utils.stringLibary.DefaultMessages;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class GameController {
    private GameStateSwitcher gameStateSwitcher;
    private StatsInterface statsInterface;
    public GameController(GameStateSwitcher gameStateSwitcher, StatsInterface statsInterface) {
        this.gameStateSwitcher = gameStateSwitcher;
        this.statsInterface = statsInterface;
    }



    public void bedBreak(Player blockBreakPlayer, Location bedloc) {
        UUID blockBreakUUID = blockBreakPlayer.getUniqueId();
        GameStatus gameStatus = MLGRush.getGameStatus();
        if (gameStatus.getGameMap().getBlueBed() == bedloc) {
            if (gameStatus.getOnlinePlayers().get(blockBreakUUID).getGameTeam().equals(GameTeam.RED)) {
                gameStatus.getOnlinePlayers().get(blockBreakUUID).setPoints(gameStatus.getOnlinePlayers().get(blockBreakUUID).getPoints() + 1);
                for (UUID uuid : gameStatus.getOnlinePlayers().keySet()) {
                    GamePlayer gamePlayer = gameStatus.getOnlinePlayers().get(uuid);
                    if (gamePlayer.getGameTeam().equals(GameTeam.BLUE)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getBlueSpawn());
                    }
                    if (gamePlayer.getGameTeam().equals(GameTeam.RED)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getRedSpawn());
                    }
                }
                statsInterface.addBrokenBeads(blockBreakUUID, 1);
                clearPlacedBlocks();
                return;
            } else {
                blockBreakPlayer.sendMessage(DefaultMessages.normalMessage("Du kannst dieses Bett nicht zerstören!"));
            }
        }

        if (gameStatus.getGameMap().getRedBed() == bedloc) {
            if (gameStatus.getOnlinePlayers().get(blockBreakUUID).getGameTeam().equals(GameTeam.BLUE)) {
                gameStatus.getOnlinePlayers().get(blockBreakUUID).setPoints(gameStatus.getOnlinePlayers().get(blockBreakUUID).getPoints() + 1);
                for (UUID uuid : gameStatus.getOnlinePlayers().keySet()) {
                    GamePlayer gamePlayer = gameStatus.getOnlinePlayers().get(uuid);
                    if (gamePlayer.getGameTeam().equals(GameTeam.BLUE)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getBlueSpawn());
                    }
                    if (gamePlayer.getGameTeam().equals(GameTeam.RED)) {
                        blockBreakPlayer.teleport(gameStatus.getGameMap().getRedSpawn());
                    }
                }
                statsInterface.addBrokenBeads(blockBreakUUID, 1);
                clearPlacedBlocks();
                return;
            } else {
                blockBreakPlayer.sendMessage(DefaultMessages.normalMessage("Du kannst dieses Bett nicht zerstören!"));
            }
        }
    }

    @Deprecated()
    public void clearPlacedBlocks() {
        for (Block block : MLGRush.getGameStatus().getSandStoneBlocks()) {
            block.setType(Material.AIR);
        }
    }

    public void endGame() {
        gameStateSwitcher.switchGamestate(GameState.ENDING);

        // Teleport to an little lobby giving coins sending tittle usw.


        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers())  {
                    MLGRush.getGameStatus().getGameMap().getEndLocation();
                }
            }
        }.runTaskLaterAsynchronously(MLGRush.getInstance(), 30*20);


        gameStateSwitcher.switchGamestate(GameState.STOPPING);
        ServiceTask serviceTask = CloudNetDriver.getInstance().getServiceTaskProvider().getServiceTask("MLGRushHub");
        for (Player player : Bukkit.getOnlinePlayers()) {
            CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getPlayerExecutor(player.getUniqueId()).connectToTask(serviceTask.getName(), ServerSelectorType.LOWEST_PLAYERS);
        }
        Bukkit.getServer().shutdown();
    }
}
