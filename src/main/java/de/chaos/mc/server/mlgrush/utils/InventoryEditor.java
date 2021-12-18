package de.chaos.mc.server.mlgrush.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class InventoryEditor {
    public void setGameInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).name("§6Schwert").unbreakable(true).itemStack());
        player.getInventory().setItem(1, new ItemBuilder(Material.STONE_PICKAXE).name("§6S").unbreakable(true).itemStack());
    }

    public void setSpectatorInventory() {

    }
}
