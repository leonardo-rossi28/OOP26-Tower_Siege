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

    private static final int MAP_W = 800;
    private static final int MAP_H = 600;
    private static final int PATH_VAL = 1;
    private static final int SPOT_VAL = 2;
    private static final int GRASS_VAL = 0;
    private static final int COORD_5 = 5;
    private static final int COORD_2 = 2;
    private static final int COORD_3 = 3;
    private static final int NEG_COORD = -1;

    private GameMap map;

    @BeforeEach
    void setUp() {
        final List<double[]> pathCoords = new ArrayList<>();
        pathCoords.add(new double[] {0, 0});
        pathCoords.add(new double[] {1, 0});

        final List<double[]> spotCoords = new ArrayList<>();
        spotCoords.add(new double[] { COORD_2, COORD_2 });
        spotCoords.add(new double[] { COORD_3, COORD_3 });

        map = new GameMapImpl(MAP_W, MAP_H, "bg.jpg", pathCoords, spotCoords, new ArrayList<>());
    }

    @Test
    void testInitialState() {
        assertEquals(MAP_W, map.getWidth());
        assertEquals(MAP_H, map.getHeight());
        assertEquals("bg.jpg", map.getBackgroundPath());

        assertEquals(2, map.getWaypoints().size());
        assertEquals(2, map.getBuildingSpots().size());

        final int[][] grid = map.getGrid();
        assertEquals(PATH_VAL, grid[0][0]); // path
        assertEquals(PATH_VAL, grid[0][1]); // path
        assertEquals(SPOT_VAL, grid[2][2]); // spot
        assertEquals(GRASS_VAL, grid[COORD_5][COORD_5]); // grass
    }

    @Test
    void testGetSpotAt() {
        final BuildingSpot spot = map.getSpotAt(2, 2);
        assertNotNull(spot);
        assertEquals(COORD_2, spot.getCol());
        assertEquals(COORD_2, spot.getRow());

        assertNull(map.getSpotAt(COORD_5, COORD_5));
        assertNull(map.getSpotAt(NEG_COORD, NEG_COORD));
    }

    @Test
    void testAddAndRemoveTower() {
        final BuildingSpot spot = map.getSpotAt(2, 2);
        final Tower tower = new TowerImpl(TowerType.BASIC);

        assertTrue(map.addTowerToSpot(tower, spot));
        assertTrue(spot.isOccupied());
        assertEquals(1, map.getTowers().size());

        final Tower tower2 = new TowerImpl(TowerType.SNIPER);
        assertFalse(map.addTowerToSpot(tower2, spot));

        map.removeTowerFromSpot(spot);
        assertFalse(spot.isOccupied());
        assertEquals(0, map.getTowers().size());
    }
}
