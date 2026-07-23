package it.unibo.towersiege.model.projectile.api;

import it.unibo.towersiege.model.tower.TowerType;

public interface Projectile {

    /**
     * Advance the projectile or applies damage on impact.
     */
    void update();

    /**
     * Returns the X coordinate of the projectile.
     * 
     * @return pixel x
     */
    double getX();

    /**
     * Returns the Y coordinate ot the projectile.
     * 
     * @return pixel Y
     */
    double getY();

    /**
     * Returns the type of the tower that fired this projectile.
     * 
     * @return source tower type
     */
    TowerType getSourceTowerType();

    /**
     * Check if the projectile is still active in the game.
     * 
     * @return true if alive
     */
    boolean isAlive();
    
}

