package de.chaos.mc.server.mlgrush.utils.megaUtils.menu;

import de.chaos.mc.server.mlgrush.utils.megaUtils.itemskulls.ItemSkullFactory;
import de.chaos.mc.server.mlgrush.utils.megaUtils.itemstacks.ItemStackFactory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class PagedMenu extends Menu
{

	int lastIndex = 0;
	HashMap<Integer, MenuItem> pagedItems = new HashMap<>();
	int currentPage = 0;
	private int effectiveSize;
	private ItemStackFactory itemStackFactory = new ItemStackFactory();
	private ItemSkullFactory itemSkullFactory = new ItemSkullFactory();
	private List<MenuItem> functionItems = new ArrayList<MenuItem>();

	public PagedMenu(int size, String title, MenuListener listener)
	{
		super(size > 36 ? 54 : size + 18, title, listener);
		this.effectiveSize = size > 36 ? 36 : size;
	}

	@Override
	public void additem(int slot, ItemStack itemStack, Consumer<Player> callback)
	{
		if (slot > lastIndex) lastIndex = slot;
		pagedItems.put(slot, new MenuItem(slot % effectiveSize, itemStack, callback));
	}

	@Override
	public void additem(MenuItem menuItem)
	{
		if (menuItem.getSlot() > lastIndex) lastIndex = menuItem.getSlot();
		super.additem(menuItem);
	}

	public void addFunctionItem(int slot, ItemStack itemStack, Consumer<Player> callback)
	{
		MenuItem menuItem = new MenuItem(slot, itemStack, callback);
		functionItems.add(menuItem);
	}

	@Override
	public void openInventory(Player p)
	{
		super.clearitems();
		for (int i = 0; i < effectiveSize; i++)
		{
			MenuItem item = pagedItems.getOrDefault(i + currentPage * effectiveSize, null);
			if (item != null) super.additem(item);
		}
		for (int i = effectiveSize; i < effectiveSize + 9; i++)
			super.additem(i, itemStackFactory.createItemStack(Material.STAINED_GLASS_PANE, " ", 1, 7), null);

		// super.additem(i, this.createItem(Material.STAINED_GLASS, " ", 1, 7), null);

		super.additem(effectiveSize + 13,
				itemStackFactory.createItemStack(Material.ITEM_FRAME,
						ChatColor.YELLOW + "Seite " + ChatColor.GRAY + "» " + ChatColor.GREEN + (currentPage + 1)
								+ ChatColor.GRAY + "/" + ChatColor.GREEN + ((lastIndex / effectiveSize) + 1),
						1, 0),
				pl ->
				{
					currentPage = currentPage > 0 ? currentPage - 1 : 0;
					openInventory(pl);
				});

		if (currentPage > 0)
			super.additem(effectiveSize + 11, itemSkullFactory.createItemSkull(
					"https://textures.minecraft.net/texture/e5da4847272582265bdaca367237c96122b139f4e597fbc6667d3fb75fea7cf6",
					ChatColor.GRAY + "Vorherige Seite"), pl ->
					{
						currentPage = currentPage > 0 ? currentPage - 1 : 0;
						openInventory(pl);
					});

		else
			super.additem(effectiveSize + 11, itemSkullFactory.createItemSkull(
					"https://textures.minecraft.net/texture/a89dd7af4c803b5287c433707c7c437cc28d521bb682c47a4d3d5d2a48afa6",
					ChatColor.GRAY + "Vorherige Seite"), pl ->
					{
						currentPage = currentPage > 0 ? currentPage - 1 : 0;
						openInventory(pl);
					});

		if (currentPage < (lastIndex / effectiveSize))
			super.additem(effectiveSize + 15, itemSkullFactory.createItemSkull(
					"https://textures.minecraft.net/texture/6527ebae9f153154a7ed49c88c02b5a9a9ca7cb1618d9914a3d9df8ccb3c84",
					ChatColor.GRAY + "Nächste Seite"), pl ->
					{
						currentPage = currentPage < (lastIndex / effectiveSize) ? (currentPage + 1) : (lastIndex / effectiveSize);
						openInventory(pl);
					});

		else
			super.additem(effectiveSize + 15, itemSkullFactory.createItemSkull(
					"https://textures.minecraft.net/texture/ea26e5ff186778eee6dbf98a15074384c3211d16be0f29460bbd964aeff",
					ChatColor.GRAY + "Nächste Seite"), pl ->
					{
						currentPage = currentPage < (lastIndex / effectiveSize) ? (currentPage + 1) : (lastIndex / effectiveSize);
						openInventory(pl);
					});

		functionItems.forEach(item -> super.additem(item) );
		super.openInventory(p);
	}
}
