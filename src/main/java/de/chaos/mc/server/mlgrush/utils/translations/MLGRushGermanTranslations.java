package de.chaos.mc.server.mlgrush.utils.translations;

import de.chaos.mc.serverapi.utils.stringLibary.AbstractMessages;
import org.bukkit.entity.Player;

public class MLGRushGermanTranslations {
    public static String youWereKilled(Player player) {
        return AbstractMessages.normalMessage("Du wurdest von " + player.getName() + " getötet!");
    }
    public static String youKilled(Player player) {
        return AbstractMessages.normalMessage("Du hast " + player.getName() + " getötet!");
    }
    public static String cantDestroyYouBed = AbstractMessages.normalMessage("Du kannst dieses Bett nicht zerstören!");
    public static String playerWon(Player player) {
        return AbstractMessages.normalMessage(player.getName() + " hat die Runde gewonnen");
    }
}
