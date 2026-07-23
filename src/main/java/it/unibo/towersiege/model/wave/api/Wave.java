package it.unibo.towersiege.model.wave.api;

import java.util.List;

import it.unibo.towersiege.model.enemy.api.Enemy;

/**
 * Interface representing the wave managment system in the game.
 */
public interface Wave {
    /**
     * Returns the total wave count.
     * 
     * @return total wave count
     */
    int getTotalWaves();

    /**
     * Generates a wave for the specified wave number.
     * 
     * @param waveNumber number of the wave
     * @return list of the spawn of the enemies
     */
    List<Enemy> generateWave(final int waveNumber);
}
