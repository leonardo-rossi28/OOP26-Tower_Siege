package it.unibo.TowerSiege.model.wave.api;

import it.unibo.TowerSiege.model.enemy.api.Enemy;
import java.util.List;

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
