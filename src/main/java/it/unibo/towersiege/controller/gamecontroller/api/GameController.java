package it.unibo.towersiege.controller.gamecontroller.api;

/**
 * Controller for the game.
 */
public interface GameController {

    /**
     * Toggles the pause state of the game.
     */
    void togglePause();

    /**
     * Restarts the game
     */
    void restartGame();
}
