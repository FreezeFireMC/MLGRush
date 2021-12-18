package de.chaos.mc.server.mlgrush.listener;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        GameStatus gameStatus = MLGRush.getGameStatus();
        if (event.getBlock().getType() == Material.BED_BLOCK || event.getBlock().getType()== Material.BED) {
            Location bedloc = event.getBlock().getLocation();
            Player blockBreakPlayer = event.getPlayer();
            MLGRush.getGameController().bedBreak(blockBreakPlayer, bedloc);
            return;
        }

        if (event.getBlock().getType().equals(Material.SANDSTONE)) {
            event.setDropItems(false);
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.SANDSTONE) {
            GameStatus gameStatus = MLGRush.getGameStatus();
            gameStatus.getSandStoneBlocks().add(event.getBlock());
        }
    }
}
