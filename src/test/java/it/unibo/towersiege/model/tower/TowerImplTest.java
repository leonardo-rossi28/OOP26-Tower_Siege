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
    
    private Tower tower;

    @BeforeEach
    void setUp() {
        tower = new TowerImpl(TowerType.BASIC);
        tower.setPosition(100, 100);
    }

    @Test
    void testInitialState() {
        assertEquals(TowerType.BASIC, tower.getType());
        assertEquals(1, tower.getLevel());
        assertTrue(tower.isAlive());
        assertEquals(100, tower.getPixelX());
        assertEquals(100, tower.getPixelY());
        assertEquals(5, tower.getDamage());
    }

    @Test
    void testUpgrade() {
        tower.upgrade();
        assertEquals(2, tower.getLevel());
        assertEquals(5 + 2, tower.getDamage()); // damage = base + (level - 1) * 2
    }

    @Test
    void testIsEnemyInRange() {
        Enemy enemy = new EnemyImpl(EnemyType.BASIC, 1);

        // Range for BASIC is 3 tiles = 120 pixels
        enemy.setPosition(100, 150); // dist = 50
        assertTrue(tower.isEnemyInRange(enemy));

        enemy.setPosition(300, 100); // dist = 200
        assertFalse(tower.isEnemyInRange(enemy));
    }

    @Test
    void testAttackAndCooldown() {
        Enemy enemy = new EnemyImpl(EnemyType.BASIC, 1);
        enemy.setPosition(100, 150);

        // Attack when off cooldown
        Projectile proj = tower.attack(enemy);
        assertNotNull(proj);

        //Attack when on cooldown
        Projectile proj2 = tower.attack(enemy);
        assertNull(proj2);

        // Tick until cooldown is over (BASIC cooldown is 60)
        for (int i = 0; i < 60; i++) {
            tower.tick();
        }

        Projectile proj3 = tower.attack(enemy);
        assertNotNull(proj3);
    }
}
