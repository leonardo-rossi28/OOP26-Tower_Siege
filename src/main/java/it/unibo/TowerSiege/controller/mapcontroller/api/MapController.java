package it.unibo.TowerSiege.controller.mapcontroller.api;

import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;

public interface MapController {
    void interactWithSpot(BuildingSpot spot);
    void sellTowerAtSpot(BuildingSpot spot);
}
