package it.unibo.towersiege.controller.maincontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.controller.gamecontroller.api.GameController;
import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.controller.maincontroller.impl.MainControllerImpl;
import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.view.gameview.api.GameView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainControllerImplTest {
    private GameModel model;
    private MainController mainController;

    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
        final GameView view = new DummyGameView();
        mainController = new MainControllerImpl(model, view);
    }
    
    @Test
    void testBeginGame() {
        mainController.beginGame();
        assertEquals(GameState.LEVEL_SELECT, model.getState());
    }

    @Test
    void testStartLevel() {
        mainController.startLevel(1);
        assertEquals(GameState.PLAYING, model.getState());
        assertEquals(1, model.getCurrentLevel());
    }

    @Test
    void testBackToLevelSelect() {
        mainController.startLevel(1);
        assertEquals(GameState.PLAYING, model.getState());

        mainController.backToLevelSelect();
        assertEquals(GameState.LEVEL_SELECT, model.getState());
    }

    private static final class DummyGameView implements GameView {

        @Override
        public void displayWelcome() { }

        @Override
        public void showStartMenu(final MainController c) { }

        @Override
        public void showLevelSelect(final MainController c, final GameModel model) { }

        @Override
        public void displayGameState(final GameModel model, final MainController mc, final GameController gc,
                final MapController mapC, final ShopController sc,
                final AbilityController ac) { }

        @Override
        public void displayEndGame(final GameState state, final MainController mc, final GameModel model) { }

        @Override
        public void showPauseMenu(final MainController mc, final GameController gc) { }

        @Override
        public void hidePauseMenu() { }

        @Override
        public  void closeGameFrame() { }
    }
}
