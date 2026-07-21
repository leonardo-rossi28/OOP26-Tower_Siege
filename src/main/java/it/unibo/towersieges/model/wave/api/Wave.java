package it.unibo.towersieges.model.wave.api;

import java.util.List;

import it.unibo.towersieges.model.enemy.api.Enemy;

public interface Wave {
    
    /**
     * @return total wave count
     */
    int getTotalWaves();

    /**
     * @param waveNumber number of the wave
     * @return list of the spawn of the enemies
     */
    List<Enemy> generateWave(int waveNumber);
}
