package de.chaos.mc.server.mlgrush.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageEvent implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            event.setDamage(0);
        } else return;
    }
}
