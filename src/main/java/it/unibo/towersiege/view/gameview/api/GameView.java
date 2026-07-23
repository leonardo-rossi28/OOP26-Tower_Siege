package it.unibo.towersiege.view.gameview.api;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.controller.gamecontroller.api.GameController;
import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;

/**
 * Interface representing the game view.
 * Responsible for all user-interface rendering and event wiring.
 */
public interface GameView {

    /**
     * Displays the welcome / splash behaviour (may be a no-op)
     */
    void displayWelcome();

    /**
     * Shows the main menu screen.
     * 
     * @param controller the controller to wire menu actions to
     */
    void showStartMenu(MainController controller);

    /**
     * Shows the level selection screen.
     * 
     * @param controller the controller to wire level-select actions to.
     * @param model      the model (used to read max unlocked level)
     */
    void showLevelSelect(MainController controller, GameModel model);

    /**
     * Renders the current game state onto the game frame.
     * Creates the frame on the first call.
     * 
     * @param model the game model to read state from
     * @param mc the main controller
     * @param gc the game controller
     * @param mapC the map controller
     * @param sc the shop controller
     * @param ac the ability controller
     */
    void displayGameState(GameModel model, MainController mc, GameController gc,
            MapController mapC, ShopController sc, AbilityController ac);

    /**
     * Displays the end-game overlay (victory or defeat).
     * 
     * @param state the final game state
     * @param mc the main controller
     * @param model the game model
     */
    void displayEndGame(GameState state, MainController mc, GameModel model);

    /**
     * Shows the pause menu dialog.
     * 
     * @param mc the main controller
     * @param gc the game controller
     */
    void showPauseMenu(MainController mc, GameController gc);

    /**
     * Hides and disposes the pause menu dialog.
     */
    void hidePauseMenu();

    /**
     * Closes and disposes the main game frame (and level select frame if open).
     */
    void closeGameFrame();
    
}