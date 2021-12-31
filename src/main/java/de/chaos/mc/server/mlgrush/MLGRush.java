package de.chaos.mc.server.mlgrush;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.server.mlgrush.commands.StatsCommand;
import de.chaos.mc.server.mlgrush.commands.setupCommand;
import de.chaos.mc.server.mlgrush.listener.*;
import de.chaos.mc.server.mlgrush.utils.MLGRushPlayerLanguage;
import de.chaos.mc.server.mlgrush.utils.config.ConfigHandler;
import de.chaos.mc.server.mlgrush.utils.config.arenaConfig.ArenaConfigHandler;
import de.chaos.mc.server.mlgrush.utils.config.generalconfig.GeneralConfig;
import de.chaos.mc.server.mlgrush.utils.gamutils.GameController;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameState;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameStateSwitcher;
import de.chaos.mc.server.mlgrush.utils.gamutils.gamestate.GameStateSwitcherRepository;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.MLGRushProfileInv;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.ormlite.UpdateInvSortingRepository;
import de.chaos.mc.server.mlgrush.utils.inventorylibary.ormlite.UpdateInventorySortingInterface;
import de.chaos.mc.server.mlgrush.utils.megaUtils.menu.MenuFactory;
import de.chaos.mc.server.mlgrush.utils.scorebaord.ScoreboardManager;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsRepository;
import de.chaos.mc.serverapi.api.ServerAPI;
import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.LanguageInterface;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class MLGRush extends JavaPlugin {
    @Getter
    private static MLGRush instance;
    @Getter private static ServerAPI api;
    @Getter private static JdbcPooledConnectionSource connectionSource;
    @Getter private static GameStatus gameStatus;
    @Getter private static GameController gameController;
    @Getter private static GameStateSwitcher gameStateSwitcher;
    private GameStateSwitcherRepository gameStateSwitcherRepository;
    public MenuFactory menuFactory;
    @Getter private ConfigHandler configHandler;
    @Getter private StatsInterface statsInterface;
    private StatsRepository statsRepository;
    private ArenaConfigHandler arenaConfigHandler;
    private GeneralConfig generalConfig;
    private UpdateInventorySortingInterface sortingInterface;
    private MLGRushProfileInv mlgRushProfileInv;
    @Getter private static HashMap<UUID, MLGRushPlayerLanguage> onlinePlayers;
    @Getter private LanguageInterface languageInterface;
    @Getter private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        instance = this;
        api = new ServerAPI();
        connectionSource = api.getConnectionSource();
        onlinePlayers = new HashMap<>();
        languageInterface = api.getLanguageInterface();

        getConfigs();

        gameStatus = new GameStatus();
        gameStatus.setGameState(GameState.LOBBY);
        gameStatus.setGameMap(configHandler.getMapObject);

        gameStateSwitcherRepository = new GameStateSwitcherRepository();
        gameStateSwitcher = gameStateSwitcherRepository;

        statsRepository = new StatsRepository(connectionSource);
        statsInterface = statsRepository;

        gameController = new GameController(gameStateSwitcher, statsInterface);
        menuFactory = MenuFactory.register(this);

        sortingInterface = new UpdateInvSortingRepository(connectionSource);

        mlgRushProfileInv = new MLGRushProfileInv(sortingInterface, menuFactory);

        scoreboardManager = new ScoreboardManager(this, languageInterface, statsInterface);

        getCommand("setup").setExecutor(new setupCommand(menuFactory, gameStatus, arenaConfigHandler));
        getCommand("stats").setExecutor(new StatsCommand(statsInterface));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ConnectionListener(mlgRushProfileInv), this);
        pluginManager.registerEvents(new ServerListPingListener(), this);
        pluginManager.registerEvents(new BlockListener(), this);
        pluginManager.registerEvents(new PlayerDamageEvent(), this);
        pluginManager.registerEvents(new PlayerMoveListener(statsInterface, mlgRushProfileInv), this);
        pluginManager.registerEvents(new EventListener(), this);
    }

    private void getConfigs() {

        arenaConfigHandler = new ArenaConfigHandler();
        generalConfig = new GeneralConfig();
        configHandler = new ConfigHandler(arenaConfigHandler, generalConfig, this);
    }


    @Override
    public void onDisable() {
    }
}