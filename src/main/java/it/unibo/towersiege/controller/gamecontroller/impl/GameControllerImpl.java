package it.unibo.towersiege.controller.gamecontroller.impl;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.controller.gamecontroller.api.GameController;
import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.view.gameview.api.GameView;
import javax.swing.Timer;

/** Implementation of the main game controller. */
public final class GameControllerImpl implements GameController{
    private static final int TICK_DELAY_MS = 16;
    private final GameModel model;
    private final GameView view;
    private final MainController mainController;

    private final Timer gameLoop;

    /**
     * Constructs a new GameControllerImpl.
     * 
     * @param model the game model
     * @param view the game view
     * @param mainController the main controller
     * @param mapController the map controller
     * @param shopController the shop contoller
     * @param abilityController the abilty controller
     */

    public GameControllerImpl(final GameModel model,
                              final GameView view,
                              final MainController mainController,
                              final MapController mapController,
                              final ShopController shopController,
                              final AbilityController abilityController) {
        this.model = model;
        this.view = view;
        this.mainController = mainController;

        this.gameLoop = new Timer(TICK_DELAY_MS, e -> {
            final Timer currentLoop = (Timer) e.getSource();
            if (model.getState() == GameState.PLAYING || model.getState() == GameState.VICTORY) {
                model.update();
            }
            view.displayGameState(model, this, mapController, shopController, abilityController);

            if (model.isVictoryRedirectReady()) {
                currentLoop.stop();
                view.closeGameFrame();
                model.setState(GameState.LEVEL_SELECT);
                view.showLevelSelect(mainController, model);
            }
        });
    }

    /**
     * Starts the loop.
     */
    public void startLoop() {
            gameLoop.start();
    }

    /**
     * Stops the loop.
     */
    public void stopLoop() {
        if (!gameLoop.isRunning()) {
            gameLoop.start();
        }
    }

    /**
     * toggles the game pause.
     */
    @Override
    public void togglePause() {
        if (model.getState() == GameState.PLAYING) {
            model.pause();
            view.showPauseMenu(mainController, this);
        }   else if (model.getState() == GameState.PAUSED) {
            model.resume();
            view.hidePauseMenu();
        }
    }

    /**
     * Restarts the game.
     */
    @Override
    public void restartGame() {
        model.start();
        view.hidePauseMenu();
        startLoop();
    }
}
