package de.chaos.mc.server.mlgrush.utils.scorebaord;

import de.chaos.mc.serverapi.utils.playerlibary.languageLibary.LanguageType;
import lombok.Builder;
import lombok.Data;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

@Data
@Builder
public class PlayerScorebaord {
    public UUID uuid;
    public Scoreboard scoreboard;
    public Objective objective;
    public Team pointsBlue;
    public Team pointsRed;
    public LanguageType languageType;
}
