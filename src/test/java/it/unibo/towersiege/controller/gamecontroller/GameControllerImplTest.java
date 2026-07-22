package it.unibo.towersiege.controller.gamecontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.controller.abilitycontroller.impl.AbilityControllerImpl;
import it.unibo.towersiege.controller.gamecontroller.api.GameController;
import it.unibo.towersiege.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.mapcontroller.impl.MapControllerImpl;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.controller.shopcontroller.impl.ShopControllerImpl;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.view.gameview.api.GameView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerImplTest {
    
    private GameModel model;
    private GameController gameController;

    // Dummy View and MainController for testing
    private static class DummyGameView implements GameView {
        @Override public void displayWelcome() {}
        @Override public void showStartMenu(MainController c) {}
        @Override public void showLevelSelect(MainController c, GameModel model) {}
        @Override public void displayGameState(GameModel model, GameController gc, MapController mapC, ShopController sc, AbilityController ac) {}
        @Override public void displayEndGame(GameState state) {}
        @Override public void showPauseMenu(MainController mc, GameController gc) {}
        @Override public void hidePauseMenu() {}
        @Override public void closeGameFrame() {}
    }

    private static class DummyMainController implements MainController {
        @Override public void start() {}
        @Override public void beginGame() {}
        @Override public void startLevel(int level) {}
        @Override public void backToMenu() {}
        @Override public void backToLevelSelect() {}
    }

    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
        model.start(); // Set to PLAYING

        GameView view = new DummyGameView();
        MainController mainController = new DummyMainController();
        ShopController shopController = new ShopControllerImpl();
        MapController mapController = new MapControllerImpl(model, shopController);
        AbilityController abilityController = new AbilityControllerImpl(model);

        gameController = new GameControllerImpl(model, view, mainController, mapController, shopController, abilityController);
    }

    @Test
    void testTogglePasue() {
        assertEquals(GameState.PLAYING, model.getState());

        gameController.togglePause();
        assertEquals(GameState.PAUSED, model.getState());

        gameController.togglePause();
        assertEquals(GameState.PLAYING, model.getState());
    }

    @Test
    void testRestartGame() {
        // Change state and model values
        model.pause();
        assertEquals(GameState.PAUSED, model.getState());

        gameController.restartGame();

        // restartGame calls model.start()
        assertEquals(GameState.PLAYING, model.getState());
        assertEquals(0, model.getCurrentWave());
        assertEquals(250, model.getPlayer().getCoins());
    }
}
