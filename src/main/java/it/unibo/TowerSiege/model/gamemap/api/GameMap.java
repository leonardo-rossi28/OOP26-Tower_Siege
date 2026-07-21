package it.unibo.towersiege.model.gamemap.api;

import java.util.List;

import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.tower.api.Tower;

public interface GameMap {

    /**
     * Returns map width
     * 
     * @return pixel width
     */
    int getWidth();

    /** 
     * Returns map height
     * 
     * @return pixel height
     */
    int getHeight();

    /** 
     * Returns the background image path
     * 
     * @return background path
     */
    String getBackgroundPath();

    /**
     * Returns the list of pixel-coordinate waypoints
     * that enemies follow
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
     * Each decaration is represented as [col,row,type] where type: 0=tree, 1=bush, 2 rock, 3=rock_bush
     * @return decorations list 
     */
    List<double[]> getDecorations();
    /**
     * Returns the grid pattern
     * Values: 0 = grass, 1= path, 2= buildable spot.
     * 
     * @return 2D grid array [row][col]
     */
    int[][] getGrid();

    /**
     * Returns all towers already placed on the map
     * 
     * @return list of placed towers
     */
    List<Tower> getTowers();

    /**
     * Returns the building spot at the given coordinates,
     * 
     * @param col
     * @param row
     * @return building spot or null
     */
    BuildingSpot getSpotAt(int col, int row);

    /**
     * Places tower on the spot.
     * 
     * @param tower
     * @param spot
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
