package de.chaos.mc.server.mlgrush.utils.scorebaord.scoreboards;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.scorebaord.PlayerScorebaord;
import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.LanguageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

public class EnglishScoreboard {
    public HashMap<UUID, PlayerScorebaord> playerScorebaordHashMap;
    public EnglishScoreboard(Plugin plugin, HashMap<UUID, PlayerScorebaord> playerScorebaordHashMap) {
        this.playerScorebaordHashMap = playerScorebaordHashMap;
        startUpdater(plugin);
    }
    public void startUpdater(Plugin plugin) {
        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : playerScorebaordHashMap.keySet()) {
                    PlayerScorebaord playerScorebaord = playerScorebaordHashMap.get(uuid);
                    if (playerScorebaord.getLanguageType() == LanguageType.DE) {
                        GameStatus gameStatus = MLGRush.getGameStatus();
                        Objective objective = playerScorebaord.getScoreboard().getObjective("Lobby");
                        playerScorebaord.getPointsRed().setSuffix("§b" + gameStatus.getTeamredPoints());
                        playerScorebaord.getPointsRed().addEntry(ChatColor.AQUA.toString());
                        playerScorebaord.getPointsBlue().setSuffix("§b" + gameStatus.getTeambluePoints());
                        playerScorebaord.getPointsBlue().addEntry(ChatColor.DARK_RED.toString());

                        objective.getScore(ChatColor.DARK_RED.toString()).setScore(4);
                        objective.getScore(ChatColor.AQUA.toString()).setScore(1);
                    }
                }
            }
        }.runTaskTimer(plugin,20, 1);
    }


    public Scoreboard getScorebaord(Player player) {
        GameStatus gameStatus = MLGRush.getGameStatus();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Lobby", "2");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("  §bFreeze§cFire  ");
        if (scoreboard.getTeam(player.getName() + ".1") != null) {
            scoreboard.getTeam(player.getName() + ".1").unregister();
        }
        if (scoreboard.getTeam(player.getName() + ".2") != null) {
            scoreboard.getTeam(player.getName() + ".2").unregister();
        }

        Team bluePoints = scoreboard.registerNewTeam(player.getName() + ".1");
        bluePoints.setPrefix("§8» §b");
        bluePoints.setSuffix("§b" + gameStatus.getTeambluePoints());
        bluePoints.addEntry(ChatColor.DARK_RED.toString());

        Team redPoints = scoreboard.registerNewTeam( player.getName() + ".2");
        redPoints.setPrefix("§8» §b");
        redPoints.setSuffix("§b" + gameStatus.getTeamredPoints());
        redPoints.addEntry(ChatColor.AQUA.toString());

        objective.getScore("§0").setScore(6);
        objective.getScore("§cTeam Blau:").setScore(5);
        objective.getScore(ChatColor.DARK_RED.toString()).setScore(4);
        objective.getScore("§1 ").setScore(3);
        objective.getScore("§cTeam Rot: ").setScore(2);
        objective.getScore(ChatColor.AQUA.toString()).setScore(1);
        objective.getScore("§3").setScore(0);


        PlayerScorebaord playerScorebaord = PlayerScorebaord.builder()
                .uuid(player.getUniqueId())
                .scoreboard(scoreboard)
                .pointsBlue(bluePoints)
                .pointsRed(redPoints)
                .languageType(LanguageType.DE)
                .build();

        playerScorebaordHashMap.put(player.getUniqueId(), playerScorebaord);
        player.setScoreboard(scoreboard);
        return scoreboard;
    }

}