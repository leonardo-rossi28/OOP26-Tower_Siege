package it.unibo.towersiege.model.tower;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.enemy.impl.EnemyImpl;
import it.unibo.towersiege.model.enemy.EnemyType;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.model.tower.impl.TowerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TowerImplTest {
    
    private static final int INITIAL_POS_X = 100;
    private static final int INITIAL_POS_Y = 100;
    private static final int INITIAL_LEVEL = 1;
    private static final int LEVEL_2 = 2;
    private static final int BASE_DAMAGE = 5;
    private static final int DAMAGE_INCREASE = 2;
    private static final int ENEMY_IN_RANGE_Y = 150;
    private static final int ENEMY_OUT_RANGE_X = 300;
    private static final int BASIC_COOLDOWN_TICKS = 60;
    private static final int WAVE = 1;

    private Tower tower;

    @BeforeEach
    void setUp() {
        tower = new TowerImpl(TowerType.BASIC);
        tower.setPosition(INITIAL_POS_X, INITIAL_POS_Y);
    }

    @Test
    void testInitialState() {
        assertEquals(TowerType.BASIC, tower.getType());
        assertEquals(INITIAL_LEVEL, tower.getLevel());
        assertTrue(tower.isAlive());
        assertEquals(INITIAL_POS_X, tower.getPixelX());
        assertEquals(INITIAL_POS_Y, tower.getPixelY());
        assertEquals(BASE_DAMAGE, tower.getDamage());
    }

    @Test
    void testUpgrade() {
        tower.upgrade();
        assertEquals(LEVEL_2, tower.getLevel());
        assertEquals(BASE_DAMAGE + DAMAGE_INCREASE, tower.getDamage());
    }

    @Test
    void testIsEnemyInRange() {
        final Enemy enemy = new EnemyImpl(EnemyType.BASIC, 1);

        enemy.setPosition(INITIAL_POS_X, ENEMY_IN_RANGE_Y);
        assertTrue(tower.isEnemyInRange(enemy));

        enemy.setPosition(ENEMY_OUT_RANGE_X, INITIAL_POS_Y);
        assertFalse(tower.isEnemyInRange(enemy));
    }

    @Test
    void testAttackAndCooldown() {
        final Enemy enemy = new EnemyImpl(EnemyType.BASIC, WAVE);
        enemy.setPosition(INITIAL_POS_X, ENEMY_IN_RANGE_Y);

        final Projectile proj = tower.attack(enemy);
        assertNotNull(proj);

        final Projectile proj2 = tower.attack(enemy);
        assertNull(proj2);

        for (int i = 0; i < BASIC_COOLDOWN_TICKS; i++) {
            tower.tick();
        }

        final Projectile proj3 = tower.attack(enemy);
        assertNotNull(proj3);
    }
}
