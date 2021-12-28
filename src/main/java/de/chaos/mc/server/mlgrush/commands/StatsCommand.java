package de.chaos.mc.server.mlgrush.commands;

import de.chaos.mc.server.mlgrush.utils.statsLibary.StatsInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;

public class StatsCommand implements CommandExecutor {
    private StatsInterface statsInterface;

    public StatsCommand(StatsInterface statsInterface) {
        this.statsInterface = statsInterface;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(AbstractMessages.normalMessage("§6Stats:"));
            player.sendMessage(AbstractMessages.normalMessage("     §6Kills: " + statsInterface.getKills(player.getUniqueId())));
            player.sendMessage(AbstractMessages.normalMessage("     §6Deaths: " + statsInterface.getDeaths(player.getUniqueId())));
            player.sendMessage(AbstractMessages.normalMessage("     §6BrokenBeds: " + statsInterface.getBrokenBed(player.getUniqueId())));
            player.sendMessage(AbstractMessages.normalMessage("     §6Wins: " + statsInterface.getWin(player.getUniqueId())));
            return true;
        }
        return false;
    }
}
