package de.chaos.mc.server.mlgrush.utils.gamutils.gamestate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GameState {
    LOBBY("LOBBY"),
    INGAME("INGAME"),
    ENDING("ENDING"),
    STOPPING("STOPPING");

    @Getter private String gamestate;
}
