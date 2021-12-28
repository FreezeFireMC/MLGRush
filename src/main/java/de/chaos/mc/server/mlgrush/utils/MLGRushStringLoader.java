package de.chaos.mc.server.mlgrush.utils;

import de.chaos.mc.server.mlgrush.MLGRush;
import de.chaos.mc.server.mlgrush.utils.translations.MLGRushEnglishTranslations;
import de.chaos.mc.server.mlgrush.utils.translations.MLGRushFrenchTranslations;
import de.chaos.mc.server.mlgrush.utils.translations.MLGRushGermanTranslations;
import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.LanguageInterface;
import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.LanguageType;

import java.util.UUID;

public class MLGRushStringLoader {
    private static LanguageInterface languageInterface = MLGRush.getInstance().getLanguageInterface();
    public static void loadLanguage(UUID uuid) {
        MLGRushPlayerLanguage playerLanguage = null;
        switch(languageInterface.getLanguageType(uuid)) {
            case EG:
                playerLanguage = MLGRushPlayerLanguage.builder()
                        .uuid(uuid)
                        .type(LanguageType.EG)
                        .cantDestroyYouBed(MLGRushEnglishTranslations.cantDestroyYouBed)
                        .build();
                MLGRush.getOnlinePlayers().put(uuid, playerLanguage);
                break;
            case DE:
                playerLanguage = MLGRushPlayerLanguage.builder()
                        .uuid(uuid)
                        .type(LanguageType.DE)
                        .cantDestroyYouBed(MLGRushGermanTranslations.cantDestroyYouBed)
                        .build();
                MLGRush.getOnlinePlayers().put(uuid, playerLanguage);
                break;
            case FR:
                playerLanguage = MLGRushPlayerLanguage.builder()
                        .uuid(uuid)
                        .type(LanguageType.FR)
                        .cantDestroyYouBed(MLGRushFrenchTranslations.cantDestroyYouBed)
                        .build();
                MLGRush.getOnlinePlayers().put(uuid, playerLanguage);
                break;
            case OTHER:
                playerLanguage = MLGRushPlayerLanguage.builder()
                        .uuid(uuid)
                        .type(LanguageType.OTHER)
                        .cantDestroyYouBed(MLGRushEnglishTranslations.cantDestroyYouBed)
                        .build();
                MLGRush.getOnlinePlayers().put(uuid, playerLanguage);
        }

    }
}
