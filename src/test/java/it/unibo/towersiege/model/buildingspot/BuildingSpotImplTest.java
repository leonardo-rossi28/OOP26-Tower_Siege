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

    private static final int COL = 2;
    private static final int ROW = 3;
    private static final double  HALF_DIV = 2.0;

    private BuildingSpot spot;

    @BeforeEach
    void setUp() {
        spot = new BuildingSpotImpl(2, 3);
    }

    @Test
    void testInitialState() {
        assertEquals(COL, spot.getCol());
        assertEquals(ROW, spot.getRow());

        double expectedX = (COL * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / HALF_DIV);
        double expectedY = (ROW * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / HALF_DIV);

        assertEquals(expectedX, spot.getPixelCenterX());
        assertEquals(expectedY, spot.getPixelCenterY());

        assertFalse(spot.isOccupied());
        assertNull(spot.getTower());
    }

    @Test
    void testSetTower() {
        final Tower tower = new TowerImpl(TowerType.BASIC);

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
