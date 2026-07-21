package it.unibo.towersieges.controller.maincontroller.api;

public interface MainController {

    /**
     * Starts the game.
     */
    void start();

    /**
     * Begins the game.
     */
    void beginGame();

    /**
     * Starts the level.
     * @param level the level
     */
    void startLevel(int level);

    /**
     * Brings back to menu.
     */
    void backToMenu();

    /**
     * Brings back to LevelSelectPanel.
     */
    void backToLevelSelect();
}
