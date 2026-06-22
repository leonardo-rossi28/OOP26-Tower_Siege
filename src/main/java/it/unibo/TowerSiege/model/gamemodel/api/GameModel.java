package it.unibo.TowerSiege.model.gamemodel.api;

import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;

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
     * @param tower
     * @return
     */
    boolean upgradeTower(Tower tower);
}
