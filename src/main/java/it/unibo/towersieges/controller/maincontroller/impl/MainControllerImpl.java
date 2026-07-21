package it.unibo.towersieges.controller.maincontroller.impl;

import it.unibo.towersieges.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersieges.controller.abilitycontroller.impl.AbilityControllerImpl;
import it.unibo.towersieges.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.towersieges.controller.maincontroller.api.MainController;
import it.unibo.towersieges.controller.mapcontroller.api.MapController;
import it.unibo.towersieges.controller.mapcontroller.impl.MapControllerImpl;
import it.unibo.towersieges.controller.shopcontroller.api.ShopController;
import it.unibo.towersieges.controller.shopcontroller.impl.ShopControllerImpl;
import it.unibo.towersieges.model.gamemodel.api.GameModel;
import it.unibo.towersieges.model.gamestate.GameState;
import it.unibo.towersieges.view.gameview.api.GameView;

/**
 * MainControllerImpl
 */
public final class MainControllerImpl implements MainController {

    private final GameModel model;
    private final GameView view;
    private final  GameControllerImpl gameControllerImpl;
    private final MapController mapController;
    private final ShopController shopController;
    private final AbilityController abilityController;

    /**
     * Main Controller cunstructor.
     * @param model the game model
     * @param view the game view
     */
    public MainControllerImpl(final GameModel model, final GameView view) {
        this.model = model;
        this.view = view;
        this.shopController = new ShopControllerImpl();
        this.mapController = new MapControllerImpl(model, shopController);
        this.abilityController = new AbilityControllerImpl(model);
        this.gameControllerImpl = new GameControllerImpl(model, view, this, mapController, shopController, abilityController);
    }

    /**
     * Starts the game.
     */
    @Override
    public void start() {
        view.displayWelcome();
        view.showStartMenu(this);
    }

    /**
     * Begins the game
     */
    @Override
    public void beginGame() {
        view.closeGameFrame();
        model.setState(GameState.LEVEL_SELECT);
        view.showLevelSelect(this, model);
    }
    /**
     * Starts the level
     */
    @Override
    public void startLevel(int level) {
        model.loadLevel(level);
        model.start();
        view.closeGameFrame();
        gameControllerImpl.startLoop();
    }

    /**
     * Bring back to menu.
     */
    @Override
    public void backToMenu() {
        gameControllerImpl.stopLoop();
        view.hidePauseMenu();
        view.closeGameFrame();
        view.showStartMenu(this);
    }

    /**
     * Brings back to level selection.
     */
    @Override
    public void backToLevelSelect() {
        gameControllerImpl.stopLoop();
        view.hidePauseMenu();
        view.closeGameFrame();
        model.setState(GameState.LEVEL_SELECT);
        view.showLevelSelect(this, model);
    }
}
