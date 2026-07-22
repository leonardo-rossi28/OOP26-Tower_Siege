package it.unibo.towersiege.model.buildingspot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.commons.gameconstants.GameConstants;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.buildingspot.impl.BuildingSpotImpl;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.model.tower.impl.TowerImpl;
import it.unibo.towersiege.model.tower.TowerType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class BuildingSpotImplTest {
    private BuildingSpot spot;

    @BeforeEach
    void setUp() {
        spot = new BuildingSpotImpl(2, 3);
    }

    @Test
    void testInitialState() {
        assertEquals(2, spot.getCol());
        assertEquals(3, spot.getRow());

        double expectedX = (2 * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
        double expectedY = (3 * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 3.0);

        assertEquals(expectedX, spot.getPixelCenterX());
        assertEquals(expectedY, spot.getPixelCenterY());

        assertFalse(spot.isOccupied());
        assertNull(spot.getTower());
    }

    @Test
    void testSetTower() {
        Tower tower = new TowerImpl(TowerType.BASIC);

        spot.setTower(tower);
        assertTrue(spot.isOccupied());
        assertSame(tower, spot.getTower());

        //Ensure tower position wass updated to match the spot
        assertEquals(spot.getPixelCenterX(), tower.getPixelX());
        assertEquals(spot.getPixelCenterY(), tower.getPixelY());

        spot.setTower(null);
        assertFalse(spot.isOccupied());
        assertNull(spot.getTower());
    }
}
