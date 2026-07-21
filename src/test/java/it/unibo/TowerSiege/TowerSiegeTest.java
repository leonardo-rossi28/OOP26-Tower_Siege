package it.unibo.TowerSiege;

import org.junit.jupiter.api.Test;

import it.unibo.towersieges.application.TowerSiege;
import it.unibo.towersieges.model.gamemodel.api.GameModel;
import it.unibo.towersieges.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersieges.model.gamestate.GameState;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
