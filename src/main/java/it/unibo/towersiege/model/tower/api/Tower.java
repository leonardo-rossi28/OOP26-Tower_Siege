package it.unibo.towersiege.model.tower.api;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.tower.TowerType;

/**
 * Represent a defense tower.
 */
public interface Tower {

    /**
     * Sets position to the given coordinates.
     * 
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
     * @param enemy the enemy
     * @return Projectile or null if on cooldown or out of range
     */
    Projectile attack(Enemy enemy);

    /**
     * Upgrade tower.
     */
    void upgrade();

    /**
     * Returns the tower type.
     * 
     * @return tower type
     */
    TowerType getType();

    /**
     * Returns the pixel x.
     * 
     * @return pixel x
     */
    double getPixelX();

    /**
     * Returns the pixel y
     * 
     * @return pixel y
     */
    double getPixelY();

    /**
     * Returns true if the enemy is alive.
     * 
     * @return true if alive
     */
    boolean isAlive();

    /**
     * Returns the range of the tower.
     * 
     * @return range
     */
    int getRange();

    /**
     * Returns the damage that the tower does.
     * 
     * @return damage
     */
    int getDamage();

    /**
     * Returns the current level.
     * 
     * @return level
     */
    int getLevel();
}
