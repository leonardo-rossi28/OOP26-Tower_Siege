package it.unibo.TowerSiege.controller.maincontroller.impl;

import it.unibo.TowerSiege.controller.maincontroller.api.MainController;
import it.unibo.TowerSiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.TowerSiege.controller.abilitycontroller.impl.AbilityControllerImpl;
import it.unibo.TowerSiege.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.TowerSiege.controller.mapcontroller.api.MapController;
import it.unibo.TowerSiege.controller.mapcontroller.impl.MapControllerImpl;
import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.controller.shopcontroller.impl.ShopControllerImpl;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.view.gameview.api.GameView;

public class MainControllerImpl implements MainController {

    private final GameModel model;
    private final GameView view;
    private final  GameControllerImpl gameControllerImpl;
    private final MapController mapController;
    private final ShopController shopController;
    private final AbilityController abilityController;

    public MainControllerImpl(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.shopController = new ShopControllerImpl();
        this.mapController = new MapControllerImpl(model, shopController);
        this.abilityController = new AbilityControllerImpl(model);
        this.gameControllerImpl = new GameControllerImpl(model, view, this, mapController, shopController, abilityController);
    }

    @Override
    public void start() {
        view.displayWelcome();
        view.showStartMenu(this);
    }

    @Override
    public void beginGame() {
        view.closeGameFrame();
        model.setState(GameState.LEVEL_SELECT);
        view.showLevelSelect(this, model);
    }

    @Override
    public void startLevel(int level) {
        model.loadLevel(level);
        model.start();
        view.closeGameFrame();
        gameControllerImpl.startLoop();
    }

    @Override
    public void backToMenu() {
        gameControllerImpl.stopLoop();
        view.hidePauseMenu();
        view.closeGameFrame();
        view.showStartMenu(this);
    }

    @Override
    public void backToLevelSelect() {
        gameControllerImpl.stopLoop();
        view.hidePauseMenu();
        view.closeGameFrame();
        model.setState(GameState.LEVEL_SELECT);
        view.showLevelSelect(this, model);
    }
}
