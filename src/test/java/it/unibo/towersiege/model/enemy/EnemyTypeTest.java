package it.unibo.towersiege.model.enemy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the EnemyType class
 */
public class EnemyTypeTest {

    private static final int BASIC_HEALTH = 80;
    private static final int BASIC_SPEED = 1;
    private static final int BASIC_REWARD = 10;
    private static final int FAST_HEALTH = 45;
    private static final int FAST_SPEED = 2;
    private static final int FAST_REWARD = 15;
    private static final int TANK_HEALTH = 200;
    private static final int TANK_SPEED = 1;
    private static final int TANK_REWARD = 25;

    @Test
    void testEnemyTypeValues() {
        assertEquals(BASIC_HEALTH, EnemyType.BASIC.getHealth());
        assertEquals(BASIC_SPEED, EnemyType.BASIC.getSpeed());
        assertEquals(BASIC_REWARD, EnemyType.BASIC.getReward());
        assertEquals("Basic Ogre", EnemyType.BASIC.stringDescription());

        assertEquals(FAST_HEALTH, EnemyType.FAST.getHealth());
        assertEquals(FAST_SPEED, EnemyType.FAST.getSpeed());
        assertEquals(FAST_REWARD, EnemyType.FAST.getReward());

        assertEquals(TANK_HEALTH, EnemyType.TANK.getHealth());
        assertEquals(TANK_SPEED, EnemyType.TANK.getSpeed());
        assertEquals(TANK_REWARD, EnemyType.TANK.getReward());
    }
}
