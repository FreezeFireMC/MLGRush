package de.chaos.mc.server.mlgrush.utils.gamutils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GameState {
    INGAME("0"),
    ENDING("1"),
    STOPPING("2");

    @Getter private String gamestate;
}
