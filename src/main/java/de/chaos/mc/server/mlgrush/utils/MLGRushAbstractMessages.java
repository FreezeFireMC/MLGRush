package de.chaos.mc.server.mlgrush.utils;

import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.translations.MLGRushEnglishTranslations;
import de.chaos.mc.server.mlgrush.utils.translations.MLGRushFrenchTranslations;
import de.chaos.mc.server.mlgrush.utils.translations.MLGRushGermanTranslations;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MLGRushAbstractMessages {
    public static String youWereKilled(UUID uuid, Player player) {
        String string = null;
        switch(MLGRush.getApi().getLanguageInterface().getLanguageType(player.getUniqueId())) {
            case EG:
                string = MLGRushEnglishTranslations.youWereKilled(player);
                break;
            case DE:
                string = MLGRushGermanTranslations.youWereKilled(player);;
            case FR:
                string = MLGRushFrenchTranslations.youWereKilled(player);
                break;
            case OTHER:
                string = MLGRushEnglishTranslations.youWereKilled(player);
                break;
        }
        return string;
    }
    public static String youKilled(UUID uuid, Player player) {
        String string = null;
        switch(MLGRush.getApi().getLanguageInterface().getLanguageType(player.getUniqueId())) {
            case EG:
                string = MLGRushEnglishTranslations.youKilled(player);
                break;
            case DE:
                string = MLGRushGermanTranslations.youKilled(player);;
            case FR:
                string = MLGRushFrenchTranslations.youKilled(player);
                break;
            case OTHER:
                string = MLGRushEnglishTranslations.youKilled(player);
                break;
        }
        return string;
    }
    public static String wonTheGame(UUID uuid, Player player) {
        String string = null;
        switch(MLGRush.getApi().getLanguageInterface().getLanguageType(player.getUniqueId())) {
            case EG:
                string = MLGRushEnglishTranslations.playerWon(player);
                break;
            case DE:
                string = MLGRushGermanTranslations.playerWon(player);;
            case FR:
                string = MLGRushFrenchTranslations.playerWon(player);
                break;
            case OTHER:
                string = MLGRushEnglishTranslations.playerWon(player);
                break;
        }
        return string;
    }
}
