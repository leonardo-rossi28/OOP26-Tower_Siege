package it.unibo.towersiege.model.projectile.api;

import it.unibo.towersiege.model.tower.TowerType;

public interface Projectile {

    /**
     * advance the projectile or applies damage on impact
     */
    void update();

    /**
     * @return pixel x
     */
    double getX();

    /**
     * @return pixel Y
     */
    double getY();

    /**
     * @return source tower type
     */
    TowerType getSourceTowerType();


    /**
     * @return true if alive
     */
    boolean isAlive();
    
}
