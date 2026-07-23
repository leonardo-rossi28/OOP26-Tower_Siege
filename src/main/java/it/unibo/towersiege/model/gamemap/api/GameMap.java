package it.unibo.towersiege.model.gamemap.api;

import java.util.List;

import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.tower.api.Tower;

/**
 * Represents the main game map, managing dimensions, building spots, and
 * entities.
 */
public interface GameMap {

    /**
     * Returns map width.
     * 
     * @return pixel width
     */
    int getWidth();

    /**
     * Returns map height.
     * 
     * @return pixel height
     */
    int getHeight();

    /**
     * Returns the background image path.
     * 
     * @return background path
     */
    String getBackgroundPath();

    /**
     * Returns the list of pixel-coordinate waypoints
     * that enemies follow.
     * 
     * @return waypoint list [x,y] array-
     */
    List<double[]> getWaypoints();

    /**
     * Returns the list of all building spots alredy on the map.
     * 
     * @return building spots
     */
    List<BuildingSpot> getBuildingSpots();

    /**
     * Returns the list of all decorations on this map.
     * 
     * @return decorations list
     */
    List<double[]> getDecorations();

    /**
     * Returns the grid pattern.
     * 
     * @return 2D grid array [row][col]
     */
    int[][] getGrid();

    /**
     * Returns all towers already placed on the map.
     * 
     * @return list of placed towers
     */
    List<Tower> getTowers();

    /**
     * Returns the building spot at the given coordinates.
     * 
     * @param col the column coordinate
     * @param row the row coordinate
     * @return building spot or null
     */
    BuildingSpot getSpotAt(int col, int row);

    /**
     * Places tower on the spot.
     * 
     * @param tower the tower to place
     * @param spot  the building spot where to place the tower
     * @return true if placed successfully
     */
    boolean addTowerToSpot(Tower tower, BuildingSpot spot);

    /**
     * Remove tower from spot.
     * 
     * @param spot spot to remove tower from
     */
    void removeTowerFromSpot(BuildingSpot spot);
}
