package de.chaos.mc.server.mlgrush.utils.gamutils;

public interface GameStateSwitcher {
    public GameState switchGamestate(GameState gameState);
    public GameState switchToNextGameState();
}
