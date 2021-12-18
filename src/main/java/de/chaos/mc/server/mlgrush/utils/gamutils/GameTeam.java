package de.chaos.mc.server.mlgrush.utils.gamutils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GameTeam {
    RED("TeamRed"),
    BLUE("TeamBlue"),
    SPECTATOR("Spectator");

    @Getter private String gameTeam;
}
