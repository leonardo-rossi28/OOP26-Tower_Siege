package it.unibo.TowerSiege.controller.abilitycontroller.impl;

import it.unibo.TowerSiege.controller.abilitycontroller.api.AbilityController;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;

public class AbilityControllerImpl implements AbilityController {

    private final GameModel model;

    public AbilityControllerImpl(GameModel model) {
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
