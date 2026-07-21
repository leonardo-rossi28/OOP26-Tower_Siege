package it.unibo.towersiege.controller.maincontroller.api;

/** Interface for the main application controller */
public interface MainController {

    /**Starts the application. */
    void start();

    /** Begins the game. */
    void beginGame();

    /**
     * Starts a specific level.
     * 
     * @param level the level
     */
    void startLevel(int level);

    /** Returns to the main menu. */
    void backToMenu();

    /** Returns to the level selection screen.*/
    void backToLevelSelect();
}
