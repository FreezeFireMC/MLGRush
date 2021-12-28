package de.chaos.mc.server.mlgrush.utils;

import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.LanguageType;
import lombok.Builder;
import lombok.Data;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
@Builder
public class MLGRushPlayerLanguage {
    public UUID uuid;
    public LanguageType type;

    public String cantDestroyYouBed;
    public String youWereKilled(UUID uuid, Player player) {
        return MLGRushAbstractMessages.youWereKilled(uuid, player);
    }
    public String youKilled(UUID uuid, Player player) {
        return MLGRushAbstractMessages.youKilled(uuid, player);
    }
    public String playerWon(UUID uuid, Player player) {
        return MLGRushAbstractMessages.wonTheGame(uuid, player);
    }
}
