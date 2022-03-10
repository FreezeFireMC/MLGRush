package de.chaos.mc.server.mlgrush.utils.inventorylibary;

import de.chaos.mc.server.mlgrush.utils.ItemBuilder;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.ormlite.UpdateInventorySortingInterface;
import de.chaos.mc.server.mlgrush.utils.megaUtils.menu.MenuFactory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class MLGRushProfileInv {
    private UpdateInventorySortingInterface invInterface;
    private MenuFactory menuFactory;
    public MLGRushProfileInv(UpdateInventorySortingInterface updateInventorySortingInterface, MenuFactory menuFactory) {
        this.invInterface = updateInventorySortingInterface;
        this.menuFactory = menuFactory;
    }

    public void setInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(invInterface.getInventory(player.getUniqueId()).getStickSlot(), new ItemBuilder(Material.STICK).enchantment(Enchantment.KNOCKBACK, 2).name("§6Stick").itemStack());
        player.getInventory().setItem(invInterface.getInventory(player.getUniqueId()).getPickaxeSlot(), new ItemBuilder(Material.STONE_PICKAXE).name("§6Spitzhacke").itemStack());
        player.getInventory().setItem(invInterface.getInventory(player.getUniqueId()).getSandstoneSlot(),new ItemBuilder(Material.SANDSTONE, 32).name("§6Sandstein").itemStack());
    }
}
