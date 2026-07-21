package it.unibo.towersiege.controller.abilitycontroller.api;

/**
 * Controller for managing abilities.
 */
public interface AbilityController {

    /**
     * Forces the next wave to start.
     */
    void forceNextWave();

    /**
     * Triggers the rain of fire ability.
     */
    void triggerRainOfFire();

    /**
     * Triggers the global freeze ability.
     */
    void triggerGlobalFreeze();
}
