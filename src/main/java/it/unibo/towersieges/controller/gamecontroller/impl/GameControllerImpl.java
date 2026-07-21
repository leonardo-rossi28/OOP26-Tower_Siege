package it.unibo.towersieges.controller.gamecontroller.impl;

import javax.swing.Timer;

import it.unibo.towersieges.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersieges.controller.gamecontroller.api.GameController;
import it.unibo.towersieges.controller.maincontroller.api.MainController;
import it.unibo.towersieges.controller.mapcontroller.api.MapController;
import it.unibo.towersieges.controller.shopcontroller.api.ShopController;
import it.unibo.towersieges.model.gamemodel.api.GameModel;
import it.unibo.towersieges.model.gamestate.GameState;
import it.unibo.towersieges.view.gameview.api.GameView;

/**
 * GameControllerImpl
 */
public final class GameControllerImpl implements GameController{
    private static final int TICK_DELAY_MS = 16;
    private final GameModel model;
    private final GameView view;
    private final MainController mainController;

    private Timer gameLoop;

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
