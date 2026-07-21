package it.unibo.towersiege.controller.mapcontroller.api;

import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;

/** Interface for map interaction controller. */
public interface MapController {
    
    /**
     * Interacts with a building spot.
     * 
     * @param spot the spot
     */
    void interactWithSpot(BuildingSpot spot);

    /**
     * Sells a tower at a building spot.
     * 
     * @param spot the building spot
     */
    void sellTowerAtSpot(BuildingSpot spot);
}
