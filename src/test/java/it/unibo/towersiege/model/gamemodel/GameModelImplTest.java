package it.unibo.towersiege.model.gamemodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersiege.model.gamestate.GameState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameModelImplTest {

    private static final int INITIAL_COINS = 250;
    private static final int FIRE_CD_MAX = 900;
    private static final int FREEZE_CD_MAX = 480;
    private static final int FIRE_CD_TICK = 899;
    private static final int FREEZE_CD_TICK = 479;

    private GameModel model;

    @BeforeEach
    void setUp() {
        // Use the base costructor if not search the files
        model = new GameModelImpl();
    }

    @Test
    void testInitialState() {
        assertEquals(GameState.MENU, model.getState());
        assertEquals(1, model.getCurrentLevel());
        assertNotNull(model.getMap());
        assertNotNull(model.getPlayer());
        assertNotNull(model.getScore());
    }

    @Test
    void testStartGame() {
        model.start();
        assertEquals(GameState.PLAYING, model.getState());
        assertEquals(0, model.getCurrentWave());
        assertFalse(model.isWaveInProgress());
        assertEquals(0, model.getActiveEnemies().size());
        assertEquals(INITIAL_COINS, model.getPlayer().getCoins()); // starting coins
    }

    @Test
    void testStateTransitions() {
        model.start();
        assertEquals(GameState.PLAYING, model.getState());

        model.pause();
        assertEquals(GameState.PAUSED, model.getState());

        model.resume();
        assertEquals(GameState.PLAYING, model.getState());

        model.setState(GameState.LEVEL_SELECT);
        assertEquals(GameState.LEVEL_SELECT, model.getState());
    }

    @Test
    void testAbilities() {
        model.start();

        assertEquals(0, model.getFireCooldown());
        model.castRainOfFire();
        assertEquals(FIRE_CD_MAX, model.getFireCooldown());

        assertEquals(0, model.getFreezeCooldown());
        model.castGlobalFreeze();
        assertEquals(FREEZE_CD_MAX, model.getFreezeCooldown());

        // Cooldown ticks decrement
        model.update();
        assertEquals(FIRE_CD_TICK, model.getFireCooldown());
        assertEquals(FREEZE_CD_TICK, model.getFreezeCooldown());
    }
}
