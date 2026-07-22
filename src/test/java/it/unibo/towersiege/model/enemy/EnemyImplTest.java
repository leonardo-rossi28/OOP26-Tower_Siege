package it.unibo.towersiege.model.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.enemy.impl.EnemyImpl;

import java.nio.file.WatchKey;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EnemyImplTest {
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        //Wave 1, BASIC enemy
        enemy = new EnemyImpl(EnemyType.BASIC, 1);
    }

    @Test
    void testInitialState() {
        assertEquals(EnemyType.BASIC, enemy.getType());
        assertEquals(80 + 8, enemy.getMaxHealth()); //wave 1 adds 8 health
        assertEquals(88, enemy.getHealth());
        assertTrue(enemy.isAlive());
        assertFalse(enemy.isReachedEnd());
        assertFalse(enemy.isCoinAwarded());
    }

    @Test
    void testTakeDamage() {
        enemy.takeDamage(10);
        assertEquals(78, enemy.getHealth());
        assertTrue(enemy.isAlive());

        enemy.takeDamage(100);
        assertEquals(0, enemy.getHealth());
        assertFalse(enemy.isAlive());
    }

    @Test
    void testMovement() {
        enemy.setPosition(0, 0);

        List<double[]> waypoints = new ArrayList<>();
        waypoints.add(new double[]{0, 0});
        waypoints.add(new double[]{10, 0});

        //Target is index 1 (10, 0)
        boolean reached = enemy.moveAlongPath(waypoints);
        assertFalse(reached);

        //Base speed for BASIC is 1
        assertEquals(1.0, enemy.getPixelX(), 0.001);
        assertEquals(0.0, enemy.getPixelY(), 0.001);
    }

    @Test
    void testSlowEffect() {
        enemy.setPosition(0, 0);
        enemy.applySlow(0.5, 10);

        List<double[]> waypoints = new ArrayList<>();
        waypoints.add(new double[]{0, 0});
        waypoints.add(new double[]{10, 0});

        enemy.moveAlongPath(waypoints);

        //Base speed 1 * slow 0.5 = 0.5
        assertEquals(0.5, enemy.getPixelX(), 0.001);

        for (int i = 0; i < 10; i++) {
            enemy.updateStatus();
        }

        //Slow should be over
        enemy.moveAlongPath(waypoints);
        assertEquals(1.5, enemy.getPixelX(), 0.001);
    }
}
