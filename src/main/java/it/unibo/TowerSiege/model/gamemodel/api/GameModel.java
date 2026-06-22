package it.unibo.TowerSiege.model.gamemodel.api;

import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.projectile.api.Projectile;

import java.util.List;

public interface GameModel {

    /**
     * Attempts to build the given tower on the given spot.
     * Deducts cost from player coins on success.
     * 
     * @param tower
     * @param spot
     * @return true if the tower was placed successfully
     */
    boolean buildTowerOnSpot(Tower tower, BuildingSpot spot);

    /**
     * Attempts to upgrade the given tower by one level.
     * Deducts upgrade cost from player coins on success.
     * @param tower tower to upgrade
     * @return ture if upgraded successfully
     */
    boolean upgradeTower(Tower tower);

    /**
     * Sells the tower on the given spot, refunding half its cost.
     * 
     * @param spot spot whose tower is sold
     * @return true if sold successfully
     */
    boolean sellTower(BuildingSpot spot);

    /** Returns a snapshot of currently active enemies. */
    List<Projectile> getProjectiles();
}
