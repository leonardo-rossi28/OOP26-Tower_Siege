package it.unibo.towersieges.model.wave.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.towersieges.model.enemy.EnemyType;
import it.unibo.towersieges.model.enemy.api.Enemy;
import it.unibo.towersieges.model.enemy.impl.EnemyImpl;
import it.unibo.towersieges.model.wave.api.Wave;

public class WaveImpl implements Wave {

    private static final int TOTAL_WAVES = 5;

    public WaveImpl() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalWaves() {
        return TOTAL_WAVES;
    }

    /**
     * {@inheriteDoc}
     * The first wave have basic enemies , in the second appear fast enemies
     * and the tank from the third wave 
     */
    @Override
    public List<Enemy> generateWave(int waveNumber) {
        final List<Enemy> enemies = new ArrayList<>();
        final int numEnemies = 4 + waveNumber * 3;
        for (int i = 0; i < numEnemies; i++) {
            EnemyType type = EnemyType.BASIC;
            if(waveNumber >= 2 && i % 3 == 0) {
                type = EnemyType.FAST;
            }
            if(waveNumber >= 3 && i % 4 == 0) { 
                type = EnemyType.TANK;
            }
            enemies.add(new EnemyImpl(type, waveNumber));
        }
        return enemies;
    }
}
