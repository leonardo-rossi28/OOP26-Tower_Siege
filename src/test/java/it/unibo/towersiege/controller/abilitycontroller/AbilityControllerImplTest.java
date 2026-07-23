package it.unibo.towersiege.controller.abilitycontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.controller.abilitycontroller.impl.AbilityControllerImpl;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;

class AbilityControllerImplTest {
    private GameModel model;
    private AbilityController abilityController;

    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
        model.start(); // Setta stato in PLAYING
        abilityController = new AbilityControllerImpl(model);
    }

    @Test
    void testForceNextWave() {
        assertEquals(0, model.getCurrentWave());
        abilityController.forceNextWave();
        assertEquals(1, model.getCurrentWave());
        assertTrue(model.isWaveInProgress());
    }

    @Test
    void testTriggerRainOfFire() {
        assertEquals(0, model.getFireCooldown());
        abilityController.triggerRainOfFire();
        assertTrue(model.getFireCooldown() > 0);
    }

    @Test
    void testTriggerGlobalFreeze() {
        assertEquals(0, model.getFreezeCooldown());
        abilityController.triggerGlobalFreeze();
        assertTrue(model.getFreezeCooldown() > 0);
    }
}
