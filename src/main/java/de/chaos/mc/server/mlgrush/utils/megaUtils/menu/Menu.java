package de.chaos.mc.server.mlgrush.utils.megaUtils.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Menu {

	protected List<MenuItem> items = new ArrayList<>();
	protected int size;

	private String title;
	protected MenuListener listener;
	private InventoryType inventoryType;
	private Inventory inv;

	protected Menu(int size, String title, MenuListener lister) {
		this.size = size;
		this.title = title;
		this.listener = lister;
	}

	protected Menu(InventoryType inventoryType, String title, MenuListener lister) {
		this.inventoryType = inventoryType;
		this.title = title;
		this.listener = lister;
	}

	public void additem(int slot, ItemStack itemStack, Consumer<Player> callback) {
		items.add(new MenuItem(slot, itemStack, callback));
	}

	public void additem(MenuItem menuItem) {
		items.add(menuItem);
	}

	public void clearitems() {
		inv.clear();
		items.clear();
	}

	public void addSubmenu(int slot, ItemStack itemStack, Menu menu) {
		items.add(new MenuItem(slot, itemStack, menu));
	}

	public void openInventory(Player p) {
		if (inventoryType == null) inv = Bukkit.createInventory(null, size, title);
		else inv = Bukkit.createInventory(null, inventoryType, title);

		items.forEach(i -> inv.setItem(i.getSlot(), i.getItemStack()));
		p.openInventory(inv);
		listener.openMenu(p, this);
	}

	public void updateInventoryItems(Player p) {
		items.forEach(i -> inv.setItem(i.getSlot(), i.getItemStack()));
//		p.openInventory(inv);
	}

	public void click(Player p, int slot) {
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		list.addAll(items);
		list.forEach(i -> {
			if (i.getSlot() == slot) i.click(p);
		});
	}
	
	public List<MenuItem> getItems()
	{
		return items;
	}
}
