package it.unibo.towersiege.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersiege.model.gamestate.GameState;

import org.junit.jupiter.api.Test;

/**
 * Test class for TowerSiege
 */
class TowerSiegeTest {
    
    @Test
    void testMain() {
        //Simple test to make sure main method can be called withou exceptions
        assertDoesNotThrow(() -> TowerSiege.main(new String[]{}));
    }

    @Test
    void testGameModelInitialization() {
        final GameModel model = new GameModelImpl();
        assertNotNull(model);
        assertEquals(GameState.MENU, model.getState());
        assertEquals(0, model.getCurrentWave());
    }
}
