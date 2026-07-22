package it.unibo.towersiege.controller.mapcontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.mapcontroller.impl.MapControllerImpl;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.controller.shopcontroller.impl.ShopControllerImpl;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.towersiege.model.tower.TowerType;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapControllerImplTest {
    
    private GameModel model;
    private ShopController shopController;
    private MapController mapController;
    private BuildingSpot spot;

    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
        model.start(); // PLAYING
        shopController = new ShopControllerImpl();
        mapController = new MapControllerImpl(model, shopController);

        //Find a valid building spot
        if (!model.getMap().getBuildingSpots().isEmpty()) {
            spot = model.getMap().getBuildingSpots().get(0);
        }
    }

    @Test
    void testInteractWithSpotBuildTower() {
        if (spot == null) return;

        shopController.setSelectedTowerType(TowerType.BASIC);

        assertFalse(spot.isOccupied());

        mapController.interactWithSpot(spot);

        assertTrue(spot.isOccupied());
        assertNotNull(spot.getTower());
        assertEquals(TowerType.BASIC, spot.getTower().getType());
    }

    @Test
    void testSellTowerAtSpot() {
        if (spot == null)  return;

        shopController.setSelectedTowerType(TowerType.BASIC);
        mapController.interactWithSpot(spot); // Build
        assertTrue(spot.isOccupied());

        mapController.sellTowerAtSpot(spot);
        assertFalse(spot.isOccupied());
    }
}
