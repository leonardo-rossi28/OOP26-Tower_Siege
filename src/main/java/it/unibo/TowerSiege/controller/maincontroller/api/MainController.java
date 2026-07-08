package it.unibo.TowerSiege.controller.maincontroller.api;

public interface MainController {
    void start();
    void beginGame();
    void startLevel(int level);
    void backToMenu();
    void backToLevelSelect();
}
