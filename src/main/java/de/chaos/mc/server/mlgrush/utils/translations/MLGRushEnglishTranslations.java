package de.chaos.mc.server.mlgrush.utils.translations;

import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;
import org.bukkit.entity.Player;

public class MLGRushEnglishTranslations {
    public static String youWereKilled(Player player) {
        return AbstractMessages.normalMessage(player.getName() + " killed you");
    }
    public static String youKilled(Player player) {
        return AbstractMessages.normalMessage("You killed " + player.getName());
    }

    public static String cantDestroyYouBed = AbstractMessages.normalMessage("You cant destroy dis bed");
    public static String playerWon(Player player) {
        return AbstractMessages.normalMessage(player.getName() + " won the Game");
    }
}