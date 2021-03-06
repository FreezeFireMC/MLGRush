package de.chaos.mc.server.mlgrush.utils.gamutils.gamestate;

import de.chaos.mc.server.mlgrush.GameStatus;
import de.chaos.mc.server.mlgrush.MLGRush;

public class GameStateSwitcherRepository implements GameStateSwitcher {
    GameStatus gameStatus;
    public GameStateSwitcherRepository() {
        this.gameStatus = MLGRush.getGameStatus();
    }


    @Override
    public GameState switchGamestate(GameState gameState) {
        gameStatus.setGameState(gameState);
        return gameState;
    }

    @Override
    public GameState switchToNextGameState() {
        switch (gameStatus.getGameState()) {
            case LOBBY:
                switchGamestate(GameState.INGAME);
                break;
            case INGAME:
                switchGamestate(GameState.ENDING);
                break;
        }
        return gameStatus.getGameState();
    }
}
