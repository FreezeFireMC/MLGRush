package de.chaos.mc.server.mlgrush.commands;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.utils.ItemBuilder;
import de.chaos.mc.server.mlgrush.utils.Permissions;
import de.chaos.mc.server.mlgrush.utils.config.ConfigHandler;
import de.chaos.mc.server.mlgrush.utils.config.sqlconfigs.ArenaConfigHandler;
import de.chaos.mc.server.mlgrush.utils.megaUtils.menu.Menu;
import de.chaos.mc.server.mlgrush.utils.megaUtils.menu.MenuFactory;
import de.chaos.mc.server.mlgrush.utils.objects.MapObject;
import de.chaos.mc.serverapi.utils.stringLibary.DefaultMessages;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

// TODO SetupCommand für Arda
public class setupCommand implements CommandExecutor{
    private MenuFactory menuFactory;
    private GameStatus gameState;
    private String mapName;
    private HashMap<String, MapObject> setupMaps = new HashMap<>();
    private ArenaConfigHandler config;
    public setupCommand(MenuFactory menuFactory, GameStatus gameState, ArenaConfigHandler config) {
        this.menuFactory = menuFactory;
        this.gameState = gameState;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(Permissions.DEVELOPER.getPermission())) {
                if (args.length == 1) {
                    String mapName = args[0];
                    MapObject mapObject;
                    if (!setupMaps.containsKey(mapName)) {
                            mapObject = MapObject.builder()
                                    .mapName(mapName)
                                    .build();
                    } else {
                        mapObject = setupMaps.get(mapName);
                    }
                    Menu menu = menuFactory.createMenu(9, "§6Setup:");
                    menu.additem(0, new ItemBuilder(Material.NETHER_STAR).name("§6Spectator Spawn").itemStack(), player1 -> {
                        mapObject.setBlueSpawn(player1.getLocation());
                        player.sendMessage(DefaultMessages.normalMessage("SpawnLocation wurde gesetzt"));
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(1, new ItemBuilder(Material.SKULL_ITEM).name("§6BlueSpawn:").itemStack(), player1 -> {
                        mapObject.setBlueSpawn(player1.getLocation());
                        player.sendMessage(DefaultMessages.normalMessage("Position-1 wurde gesetzt"));
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(2, new ItemBuilder(Material.SKULL_ITEM).name("§6RedSpawn:").itemStack(), player1 -> {
                        mapObject.setRedSpawn(player1.getLocation());
                        player.sendMessage(DefaultMessages.normalMessage("Position-2 wurde gesetzt"));
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(3, new ItemBuilder(Material.SKULL_ITEM).name("§6BlueBed").itemStack(), player1 -> {
                        mapObject.setBlueBed(player1.getLocation());
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(4, new ItemBuilder(Material.SKULL_ITEM).name("§6RedBed").itemStack(), player1 -> {
                        mapObject.setRedBed(player1.getLocation());
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(5, new ItemBuilder(Material.SKULL_ITEM).name("§6EndLocation").itemStack(), player1 -> {
                        mapObject.setEndLocation(player1.getLocation());
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(6, new ItemBuilder(Material.SKULL_ITEM).name("§6SetY").itemStack(), player1 -> {
                        mapObject.setDeathYCordinate(player1.getLocation().getBlockY());
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();
                    });
                    menu.additem(7, new ItemBuilder(Material.SKULL_ITEM).name("§cSchließen").itemStack(), player1 -> {
                        setupMaps.put(mapName, mapObject);
                        player1.closeInventory();});
                    menu.additem(8, new ItemBuilder(Material.SKULL_ITEM).name("§sBestätigen").itemStack(), player1 -> {
                        player1.closeInventory();
                        config.saveMap(mapObject);
                        setupMaps.remove(mapObject.getMapName());
                            player.sendMessage(DefaultMessages.normalMessage("Map wurde hinzugefügt"));
                    });
                    menu.openInventory(player);
                } else {
                    player.sendMessage(DefaultMessages.wrongSyntax("/setup [MapName]"));
                }
            } else {
                player.sendMessage(DefaultMessages.NOPERMISSION);
            }
        } else {
            sender.sendMessage(DefaultMessages.BEAPLAYER);
        }
        return false;
    }
}
