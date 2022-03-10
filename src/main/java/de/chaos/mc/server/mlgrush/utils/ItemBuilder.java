package de.chaos.mc.server.mlgrush.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class ItemBuilder {
    private final ItemStack itemStack;
    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
    }
    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }
    public ItemBuilder(Material material, int amount, int shortID) { itemStack = new ItemStack(material, amount, (short) shortID); }
    public ItemBuilder(Material material, int amount, int shortID, DyeColor colour) { itemStack = new ItemStack(material, amount, (short) shortID, colour.getDyeData()); }



    public ItemBuilder name(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder skullOwner(String name) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(name);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder unbreakable(boolean statement) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(statement);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setlore(String lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Collections.singletonList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    @Deprecated
    public ItemBuilder setDyeColour(DyeColor dyeColour) {;
        return null;
    }

    public ItemStack itemStack() {
        return itemStack;
    }
}
