package de.chaos.mc.server.mlgrush.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerWeather(WeatherType.CLEAR);
        }
    }
    @EventHandler
    public void onMeltEvent(BlockPhysicsEvent event) {
        if(event.getBlock().getType() == Material.SNOW_BLOCK && event.getBlock().getType() == Material.WATER){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onplayerEntityInteractEvent(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onHungerDropEvent(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }
}