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

    private static final int EXPECTED_INITIAL_COINS = 250;

    private GameModel model;
    private GameController gameController;

    @BeforeEach
    final void setUp() {
        model = new GameModelImpl();
        model.start();

        final GameView view = new DummyGameView();
        final MainController mainController = new DummyMainController();
        final ShopController shopController = new ShopControllerImpl();
        final MapController mapController = new MapControllerImpl(model, shopController);
        final AbilityController abilityController = new AbilityControllerImpl(model);

        gameController = new GameControllerImpl(model, view, mainController,
                mapController, shopController, abilityController);
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

        model.pause();
        assertEquals(GameState.PAUSED, model.getState());

        gameController.restartGame();

        assertEquals(GameState.PLAYING, model.getState());
        assertEquals(0, model.getCurrentWave());
        assertEquals(EXPECTED_INITIAL_COINS, model.getPlayer().getCoins());
    }

    // Dummy view and MainController for testing
    private static final class DummyGameView implements GameView {

        @Override
        public void displayWelcome() {
        }

        @Override
        public void showStartMenu(final MainController c) {
        }

        @Override
        public void showLevelSelect(final MainController c, final GameModel model) {
        }

        @Override
        public void displayGameState(final GameModel model, final MainController mc, final GameController gc,
                final MapController mapC, final ShopController sc,
                final AbilityController ac) {
        }

        @Override
        public void displayEndGame(final GameState state, final MainController mc, final GameModel model) {
        }

        @Override
        public void showPauseMenu(final MainController mc, final GameController gc) {
        }

        @Override
        public void hidePauseMenu() {
        }

        @Override
        public void closeGameFrame() {
        }
    }

    private static final class DummyMainController implements MainController {

        @Override
        public void start() {
        }

        @Override
        public void beginGame() {
        }

        @Override
        public void startLevel(final int level) {
        }

        @Override
        public void backToMenu() {
        }

        @Override
        public void backToLevelSelect() {
        }
    }
}
