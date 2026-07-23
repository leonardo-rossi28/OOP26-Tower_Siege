package it.unibo.towersiege.model.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.enemy.impl.EnemyImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EnemyImplTest {

    private static final int WAVE = 1;
    private static final int BASE_HEALTH = 80;
    private static final int WAVE_ADD_HEALTH = 8;
    private static final int INITIAL_HEALTH = BASE_HEALTH + WAVE_ADD_HEALTH;
    private static final int DMG_1 = 10;
    private static final int EXPECTED_HEALTH_1 = 78;
    private static final int DMG_FATAL = 100;
    private static final int WP0_X = 0;
    private static final int WP0_Y = 0;
    private static final int WP1_X = 10;
    private static final int WP1_Y = 0;
    private static final double DELTA = 0.001;
    private static final double SLOW_MUL = 0.5;
    private static final int SLOW_TICKS = 10;
    private static final double EXPECTED_X_SLOW = 0.5;
    private static final double EXPECTED_X_REC = 1.5;
    private static final double BASE_SPEED_BASIC = 1.0;

    private Enemy enemy;

    @BeforeEach
    void setUp() {
        enemy = new EnemyImpl(EnemyType.BASIC, WAVE);
    }

    @Test
    void testInitialState() {
        assertEquals(EnemyType.BASIC, enemy.getType());
        assertEquals(INITIAL_HEALTH, enemy.getMaxHealth());
        assertEquals(INITIAL_HEALTH, enemy.getHealth());
        assertTrue(enemy.isAlive());
        assertFalse(enemy.isReachedEnd());
        assertFalse(enemy.isCoinAwarded());
    }

    @Test
    void testTakeDamage() {
        enemy.takeDamage(DMG_1);
        assertEquals(EXPECTED_HEALTH_1, enemy.getHealth());
        assertTrue(enemy.isAlive());

        enemy.takeDamage(DMG_FATAL);
        assertEquals(0, enemy.getHealth());
        assertFalse(enemy.isAlive());
    }

    @Test
    void testMovement() {
        enemy.setPosition(WP0_X, WP0_Y);

        final List<double[]> waypoints = new ArrayList<>();
        waypoints.add(new double[] {WP0_X, WP0_Y});
        waypoints.add(new double[] {WP1_X, WP1_Y});

        final boolean reached = enemy.moveAlongPath(waypoints);
        assertFalse(reached);

        assertEquals(BASE_SPEED_BASIC, enemy.getPixelX(), DELTA);
        assertEquals(WP0_Y, enemy.getPixelY(), DELTA);
    }

    @Test
    void testSlowEffect() {
        enemy.setPosition(WP0_X, WP0_Y);
        enemy.applySlow(SLOW_MUL, SLOW_TICKS);

        final List<double[]> waypoints = new ArrayList<>();
        waypoints.add(new double[] {WP0_X, WP0_Y});
        waypoints.add(new double[] {WP1_X, WP1_X});

        enemy.moveAlongPath(waypoints);

        assertEquals(EXPECTED_X_SLOW, enemy.getPixelX(), DELTA);

        for (int i = 0; i < SLOW_TICKS; i++) {
            enemy.updateStatus();
        }

        // Slow should be over
        enemy.moveAlongPath(waypoints);
        assertEquals(EXPECTED_X_REC, enemy.getPixelX(), DELTA);
    }
}
