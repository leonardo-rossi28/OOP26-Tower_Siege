package it.unibo.towersiege.model.wave;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.enemy.EnemyType;
import it.unibo.towersiege.model.wave.api.Wave;
import it.unibo.towersiege.model.wave.impl.WaveImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WaveImplTest {

    private static final int TOTAL_WAVES = 5;
    private static final int WAVE_1 = 1;
    private static final int WAVE_3 = 3;
    private static final int WAVE_1_SIZE = 7;
    private static final int WAVE_3_SIZE = 13;

    private Wave wave;

    @BeforeEach
    void setUp() {
        wave = new WaveImpl();
    }

    @Test
    void testTotalWaves() {
        assertEquals(TOTAL_WAVES, wave.getTotalWaves());
    }

    @Test
    void testGenerateWave1() {
        final List<Enemy> enemies = wave.generateWave(WAVE_1);
        assertEquals(WAVE_1_SIZE, enemies.size());

        for (final Enemy e : enemies) {
            assertEquals(EnemyType.BASIC, e.getType());
        }
    }

    @Test
    void testGenerateWave3() {
        final List<Enemy> enemies = wave.generateWave(WAVE_3);
        assertEquals(WAVE_3_SIZE, enemies.size());

        boolean hasFast = false;
        boolean hasTank = false;

        for (final Enemy e : enemies) {
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
