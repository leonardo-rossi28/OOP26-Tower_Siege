package it.unibo.towersiege.model.wave;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.enemy.EnemyType;
import it.unibo.towersiege.model.wave.api.Wave;
import it.unibo.towersiege.model.wave.impl.WaveImpl;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WaveImplTest {
    
    private Wave wave;

    @BeforeEach
    void setUp() {
        wave = new WaveImpl();
    }

    @Test
    void testTotalWaves() {
        assertEquals(5, wave.getTotalWaves());
    }

    @Test
    void testGenerateWave1() {
        List<Enemy> enemies = wave.generateWave(1);
        assertEquals(4 + 3, enemies.size()); // 4 + 1 * 3 = 7

        for (Enemy e : enemies) {
            assertEquals(EnemyType.BASIC, e.getType());
        }
    }

    @Test
    void testGenerateWave3() {
        List<Enemy> enemies = wave.generateWave(3);
        assertEquals(4 + 9, enemies.size()); // 4 + 3 * 3 = 13

        boolean hasFast = false;
        boolean hasTank = false;

        for (Enemy e : enemies) {
            if (e.getType() == EnemyType.FAST) {
                hasFast = true;
            }
            if (e.getType() == EnemyType.TANK) {
                hasTank = true;
            }
        }

        assertTrue(hasFast);
        assertTrue(hasTank);
    }
}
