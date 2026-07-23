package it.unibo.towersiege.model.tower.api;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.tower.TowerType;

/**
 * Represent a defense tower.
 */
public interface Tower {

    /**
     * @param x the pixel x coordinate
     * @param y the pixel y coordinate
     */
    void setPosition(double x, double y);

    /**
     * Update the internal state of the tower.
     */
    void tick();

    /**
     * Check if the specified enemy is within the tower attack range.
     * 
     * @param enemy enemy to check
     * @return true if enemy is in range
     */
    boolean isEnemyInRange(Enemy enemy);

    /**
     * Attempts to fire the enemy.
     * 
     * @param enemy
     * @return Projectile or null if on cooldown or out of range
     */
    Projectile attack(Enemy enemy);

    /** 
     * Upgrade tower.
     */
    void upgrade();

    /**
     * @return tower type
     */
    TowerType getType();

    /**
     * @return pixel x
     */
    double getPixelX();

    /**
     * @return pixel y
     */
    double getPixelY();

    /**
     * @return true if alive
     */
    boolean isAlive();

    /**
     * @return range
     */
    int getRange();

    /**
     * @return damage
     */
    int getDamage();

    /**
     * @return level
     */
    int getLevel();
}
