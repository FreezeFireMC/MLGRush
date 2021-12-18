package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.MLGRush;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String motd = MLGRush.getGameStatus().getGameState().toString();
        event.setMotd(motd);
    }
}
