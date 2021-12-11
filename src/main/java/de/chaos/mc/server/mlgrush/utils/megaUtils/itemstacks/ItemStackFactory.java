package de.chaos.mc.server.mlgrush.utils.megaUtils.itemstacks;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemStackFactory {
	
	public ItemStack createItemStack(Material material, String displayName, int amount, int data, String... lore) {
		ItemStack itemStack = new ItemStack(material, amount, (short) data);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);

		itemMeta.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}
	
	public ItemStack addFlagsToItemStack(ItemStack itemStack, ItemFlag itemFlag) {
		ItemStack i = itemStack;
		ItemMeta itemMeta = i.getItemMeta();
		
		itemMeta.addItemFlags(itemFlag);
		i.setItemMeta(itemMeta);
		
		return i;
	}
	
	public ItemStack addEntchantmentsToItemStack(ItemStack itemStack, Enchantment enchantment, int level, boolean ignoreLevelLimit) {
		ItemStack i = itemStack;
		ItemMeta itemMeta = i.getItemMeta();
		
		itemMeta.addEnchant(enchantment, level, ignoreLevelLimit);
		i.setItemMeta(itemMeta);
		
		return i;
	}
}
