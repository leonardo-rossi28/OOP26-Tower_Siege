package it.unibo.towersieges.controller.mapcontroller.api;

import it.unibo.towersieges.model.buildingspot.api.BuildingSpot;

public interface MapController {
    /**
     * Interacts with a given spot.
     * @param spot the spot
     */
    void interactWithSpot(BuildingSpot spot);

    /**
     * Sells a tower in a given spot.
     * @param spot the spot
     */
    void sellTowerAtSpot(BuildingSpot spot);
}
