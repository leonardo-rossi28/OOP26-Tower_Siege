package it.unibo.towersieges.view.gameview.api;

import it.unibo.towersieges.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersieges.controller.gamecontroller.api.GameController;
import it.unibo.towersieges.controller.maincontroller.api.MainController;
import it.unibo.towersieges.controller.mapcontroller.api.MapController;
import it.unibo.towersieges.controller.shopcontroller.api.ShopController;
import it.unibo.towersieges.model.gamemodel.api.GameModel;
import it.unibo.towersieges.model.gamestate.GameState;

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
     * @param model     the game model to read state from
     * @param controller the controller to wire input actions to.
     */
    void displayGameState(GameModel model, GameController gc, MapController mapC, ShopController sc, AbilityController ac);

    /**
     * Displays the end-game overlay (victory or defeat)
     * 
     * @param state the final game state
     */
    void displayEndGame(GameState state);

    /**
     * Shows the pause menu dialog
     * 
     * @param controller the controller to wire pause-menu actions to
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