package de.chaos.mc.server.mlgrush.utils.gamutils;

import de.chaos.mc.server.mlgrush.MLGRush;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceTask;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameController {
    GameStateSwitcher gameStateSwitcher;
    public GameController(GameStateSwitcher gameStateSwitcher) {
        this.gameStateSwitcher = gameStateSwitcher;
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
