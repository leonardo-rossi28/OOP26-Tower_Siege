package it.unibo.towersiege.controller.abilitycontroller.impl;

import it.unibo.towersiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;

public final class AbilityControllerImpl implements AbilityController {

    private final GameModel model;

    /**
     * Constructs a new AbilityControllerImpl.
     * 
     * @param model the game model
     */
    public AbilityControllerImpl(final GameModel model) {
        this.model = model;
    }

    @Override
    public void forceNextWave() {
        if (model.getState() == GameState.PLAYING) {
            model.startNextWave();
        }
    }

    @Override
    public void triggerRainOfFire() {
        model.castRainOfFire();
    }

    @Override
    public void triggerGlobalFreeze() {
        model.castGlobalFreeze();
    }
}
