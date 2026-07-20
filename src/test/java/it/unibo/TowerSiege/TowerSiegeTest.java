package it.unibo.TowerSiege;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.TowerSiege.application.TowerSiege;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.TowerSiege.model.gamestate.GameState;

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
        GameModel model = new GameModelImpl();
        assertNotNull(model);
        assertEquals(GameState.MENU, model.getState());
        assertEquals(0, model.getCurrentWave());
    }
}
