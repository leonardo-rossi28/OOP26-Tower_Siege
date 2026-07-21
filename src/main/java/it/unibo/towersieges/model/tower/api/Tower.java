package it.unibo.towersieges.model.tower.api;

import it.unibo.towersieges.model.enemy.api.Enemy;
import it.unibo.towersieges.model.projectile.api.Projectile;
import it.unibo.towersieges.model.tower.TowerType;




public interface Tower {

    /**
     * @param x
     * @param y
     */
    void setPosition(double x,double y);

    void tick();

    /**
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
     * upgrade tower
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
