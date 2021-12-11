package de.chaos.mc.server.mlgrush;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.server.mlgrush.commands.setupCommand;
import de.chaos.mc.server.mlgrush.listener.ConnectionListener;
import de.chaos.mc.server.mlgrush.utils.config.ConfigHandler;
import de.chaos.mc.server.mlgrush.utils.config.sqlconfigs.ArenaConfigHandler;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameController;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameState;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameStateSwitcher;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameStateSwitcherRepository;
import de.chaos.mc.server.mlgrush.utils.megaUtils.menu.MenuFactory;
import de.chaos.mc.serverapi.api.ServerAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MLGRush extends JavaPlugin {
    @Getter private static MLGRush instance;
    @Getter private static ServerAPI api;
    @Getter private static JdbcPooledConnectionSource connectionSource;
    @Getter private static GameStatus gameStatus;
    @Getter private static GameController gameController;
    @Getter private static GameStateSwitcher gameStateSwitcher;
    private GameStateSwitcherRepository gameStateSwitcherRepository;
    public MenuFactory menuFactory;
    private ConfigHandler configHandler;
    private ArenaConfigHandler arenaConfigHandler;

    @Override
    public void onEnable() {
        instance = this;
        api = new ServerAPI();
        connectionSource = api.getConnectionSource();

        gameStatus = new GameStatus();
        gameStatus.setGameState(GameState.INGAME);
        gameStatus.setGameMap(null);

        gameStateSwitcherRepository = new GameStateSwitcherRepository();
        gameStateSwitcher = gameStateSwitcherRepository;

        gameController = new GameController(gameStateSwitcher);
        menuFactory = MenuFactory.register(this);

        arenaConfigHandler = new ArenaConfigHandler();
        configHandler = new ConfigHandler(arenaConfigHandler, this);


        getCommand("setup").setExecutor(new setupCommand(menuFactory, gameStatus, arenaConfigHandler));
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ConnectionListener(), this);
    }


    @Override
    public void onDisable() {
    }
}