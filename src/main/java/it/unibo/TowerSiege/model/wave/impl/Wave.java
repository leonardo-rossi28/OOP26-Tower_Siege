package it.unibo.TowerSiege.model.wave.impl;

import java.util.List;

import it.unibo.TowerSiege.model.enemy.EnemyType;
import it.unibo.TowerSiege.model.enemy.impl.EnemyImpl;

import java.util.ArrayList;

public class Wave {
    private final int totalWaves = 5;

    public Wave() {}
    public int getTotalWaves() {
        return totalWaves;
    }

    public List<EnemyImpl> generateWave(int waveNumber) {
        List<EnemyImpl> enemies = new ArrayList<>();
        int numEnemies = 4 + waveNumber * 3;
        for(int i = 0; i < numEnemies; i++) {
            EnemyType type = EnemyType.BASIC;
            if(waveNumber >= 2 && i%3 == 0) type = EnemyType.FAST;
            if(waveNumber >= 3 && i%4 == 0) type = EnemyType.TANK;
            enemies.add(new EnemyImpl(type, waveNumber));
        }
        return enemies;
    }
}
