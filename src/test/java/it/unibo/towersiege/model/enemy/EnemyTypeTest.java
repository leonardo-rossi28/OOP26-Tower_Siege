package it.unibo.towersiege.model.enemy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTypeTest {
    @Test
    void testEnemyTypeValues() {
        assertEquals(80, EnemyType.BASIC.getHealth());
        assertEquals(1, EnemyType.BASIC.getSpeed());
        assertEquals(10, EnemyType.BASIC.getReward());
        assertEquals("Orco Raider", EnemyType.BASIC.StringDescription());

        assertEquals(45, EnemyType.FAST.getHealth());
        assertEquals(2, EnemyType.FAST.getSpeed());
        assertEquals(15, EnemyType.FAST.getReward());

        assertEquals(200, EnemyType.TANK.getHealth());
        assertEquals(1, EnemyType.TANK.getSpeed());
        assertEquals(25, EnemyType.TANK.getReward());
    }
}
