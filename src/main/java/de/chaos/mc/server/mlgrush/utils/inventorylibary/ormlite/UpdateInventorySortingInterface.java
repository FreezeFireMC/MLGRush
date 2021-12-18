package de.chaos.mc.server.mlgrush.utils.inventorylibary.ormlite;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public interface UpdateInventorySortingInterface {
    public InventoryDAO getInventory(UUID uuid);
    public void updateInventory(InventoryDAO inventoryDAO);
    public void checkIfFirstJoin(UUID uuid);
    public void updateSorting(Player player, Inventory inventory);
}
