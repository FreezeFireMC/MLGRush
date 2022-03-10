package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.MLGRush;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {
    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        if (event.getCommand() == "l") {
            event.setCancelled(true);
            MLGRush.getGameController().endGame();
        }
        if (event.getCommand() == "lobby") {
            event.setCancelled(true);
            MLGRush.getGameController().endGame();
        }
        if (event.getCommand() == "leave") {
            event.setCancelled(true);
            MLGRush.getGameController().endGame();
        }
        if (event.getCommand() == "hub") {
            event.setCancelled(true);
            MLGRush.getGameController().endGame();
        }
    }
}
