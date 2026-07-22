package it.unibo.towersiege.model.projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.enemy.impl.EnemyImpl;
import it.unibo.towersiege.model.enemy.EnemyType;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.projectile.impl.ProjectileImpl;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.model.tower.impl.TowerImpl;
import it.unibo.towersiege.model.tower.TowerType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProjectileImplTest {
    
    private Tower source;
    private Enemy target;
    private Projectile projectile;

    @BeforeEach
    void setUp() {
        source = new TowerImpl(TowerType.BASIC);
        source.setPosition(0, 0);

        target = new EnemyImpl(EnemyType.BASIC, 1);
        target.setPosition(30, 0); //Speed is 15.0, distance is 30.0 + 20.0 offset

        projectile = new ProjectileImpl(source, target);
    }

    @Test
    void testInitialState() {
        assertTrue(projectile.isAlive());
        assertEquals(TowerType.BASIC, projectile.getSourceTowerType());
        assertEquals(0, projectile.getX());
        assertEquals(0, projectile.getY());
    }

    @Test
    void testUpdateAndImpact() {
        int initialHealth = target.getHealth();

        //Target is at 30, offset is 20, so target center is at 50
        //Speed is 15.0 per tick

        projectile.update();
        assertTrue(projectile.isAlive());
        assertEquals(initialHealth, target.getHealth());

        //Loop until impact
        int ticks = 0;
        while (projectile.isAlive() && ticks < 20) {
            projectile.update();
            ticks++;
        }

        assertFalse(projectile.isAlive());
        assertEquals(initialHealth - source.getDamage(), target.getHealth());
    }

    @Test
    void testDeadTarget() {
        target.takeDamage(1000); //Kill target
        projectile.update();
        assertFalse(projectile.isAlive()); //Projectile should die
    }
}
