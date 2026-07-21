package it.unibo.towersieges.model.projectile.api;

import it.unibo.towersieges.model.tower.TowerType;

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
