package it.unibo.towersiege.model.gamemap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.gamemap.api.GameMap;
import it.unibo.towersiege.model.gamemap.impl.GameMapImpl;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.model.tower.impl.TowerImpl;
import it.unibo.towersiege.model.tower.TowerType;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameMapImplTest {
    private GameMap map;

    @BeforeEach
    void setUp() {
        List<double[]> pathCoords = new ArrayList<>();
        pathCoords.add(new double[]{0, 0});
        pathCoords.add(new double[]{1, 0});

        List<double[]> spotCoords = new ArrayList<>();
        spotCoords.add(new double[]{2, 2});
        spotCoords.add(new double[]{3, 3});

        map = new GameMapImpl(800, 600, "bg.jpg", pathCoords, spotCoords, new ArrayList<>());
    }

    @Test
    void testInitialState() {
        assertEquals(800, map.getWidth());
        assertEquals(600, map.getHeight());
        assertEquals("bg.jpg", map.getBackgroundPath());

        assertEquals(2, map.getWaypoints().size());
        assertEquals(2, map.getBuildingSpots().size());

        int[][] grid = map.getGrid();
        assertEquals(1, grid[0][0]); //path
        assertEquals(1, grid[0][1]); //path
        assertEquals(2, grid[2][2]); //spot
        assertEquals(0, grid[5][5]); //grass
    }

    @Test
    void testGetSpotAt() {
        BuildingSpot spot = map.getSpotAt(2, 2);
        assertNotNull(spot);
        assertEquals(2, spot.getCol());
        assertEquals(2, spot.getRow());

        assertNull(map.getSpotAt(5, 5));
        assertNull(map.getSpotAt(-1, -1));
    }

    @Test
    void testAddAndRemoveTower() {
        BuildingSpot spot = map.getSpotAt(2, 2);
        Tower tower = new TowerImpl(TowerType.BASIC);

        assertTrue(map.addTowerToSpot(tower, spot));
        assertTrue(spot.isOccupied());
        assertEquals(1, map.getTowers().size());

        //Cannot add to an already occupied spot 
        Tower tower2 = new TowerImpl(TowerType.SNIPER);
        assertFalse(map.addTowerToSpot(tower2, spot));

        map.removeTowerFromSpot(spot);
        assertFalse(spot.isOccupied());
        assertEquals(0, map.getTowers().size());
    }
}
