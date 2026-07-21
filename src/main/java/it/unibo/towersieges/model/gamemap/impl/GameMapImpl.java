package it.unibo.towersieges.model.gamemap.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.towersieges.commons.gameconstants.GameConstants;
import it.unibo.towersieges.model.buildingspot.api.BuildingSpot;
import it.unibo.towersieges.model.buildingspot.impl.BuildingSpotImpl;
import it.unibo.towersieges.model.gamemap.api.GameMap;
import it.unibo.towersieges.model.tower.api.Tower;

/**
 * Represents the grid-based game map containing the enemy path,
 * building spots and placed towers
 */

public class GameMapImpl implements GameMap{
    
    private final int width;
    private final int height;
    private final String backgroundPath;

    /** Pixel-center waypoints for enemy movements. */
    private final List<double[]> pixelWaypoints;
    private final List<BuildingSpot> buildingSpots;
    private final List<double[]> decorations;

    /** Grid: 0 = Grass, 1 = Path, 2 = Buildable Spot. */
    private final int[][] grid;

    /**
     * Consturct a GameMap from the grid-coordinate data
     * @param width map width
     * @param height map height
     * @param backgroundPath background path image
     * @param pathGridCoords [col][row] waypoints for enemy path
     * @param spotGridCoords [col][row] list for building spots
     * @param decorationCoords list of [col,row,type] positions for decorations
     */
    public GameMapImpl(final int width, final int height, final String backgroundPath, final List<double[]> pathGridCoords, final List <double[]> spotGridCoords, final List<double[]> decorationCoords){
        this.width = width;
        this.height = height;
        this.backgroundPath = backgroundPath != null ? backgroundPath : "";

        this.grid = new int[GameConstants.ROWS][GameConstants.COLS];
        this.pixelWaypoints = new ArrayList<>();
        this.buildingSpots = new ArrayList<>();
        this.decorations= decorationCoords != null ? new ArrayList<>(decorationCoords):new ArrayList<>();

        if (pathGridCoords != null) {
            for (double[] coord : pathGridCoords) {
                int col = (int) coord[0];
                int row = (int) coord[1];
                
                if(col >= 0 && col < GameConstants.COLS && row >= 0 && row < GameConstants.ROWS) {
                    grid[row][col] = 1;
                    pixelWaypoints.add(new double[]{
                        (col * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0),
                        (row * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0)
                    });
                }
            }
        }

        if (spotGridCoords != null) {
            for (double[] coord : spotGridCoords) {
                int col = (int) coord[0];
                int row = (int) coord[1];

                if (col >= 0 && col < GameConstants.COLS && row >= 0 && row < GameConstants.ROWS) {
                    grid[row][col] = 2;
                    buildingSpots.add(new BuildingSpotImpl(col, row));
                }
            }
        }
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public String getBackgroundPath() {
        return backgroundPath;
    }


    /**
     * {@inheritDOc}
     */
    @Override
    public List<double[]> getWaypoints() {
        return pixelWaypoints;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public List<BuildingSpot> getBuildingSpots() {
        return buildingSpots;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public int[][] getGrid() {
        return grid;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public List<Tower> getTowers() {
        return buildingSpots.stream()
        .filter(BuildingSpot::isOccupied)
        .map(BuildingSpot::getTower)
        .collect(Collectors.toList());
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public BuildingSpot getSpotAt(int col, int row) {
        if (col < 0 || col >= GameConstants.COLS || row < 0 || row >= GameConstants.ROWS) {
            return null;
        }
        for (BuildingSpot spot : buildingSpots) {
            if (spot.getCol() == col && spot.getRow() == row) {
                return spot;
            }
        }
        return null;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public boolean addTowerToSpot(Tower tower, BuildingSpot spot) {
        if (!spot.isOccupied()) {
            spot.setTower(tower);
            return true;
        }
        return false;
    }
    
    /**
     * {@inheritDOc}
     */
    @Override
    public void removeTowerFromSpot(final BuildingSpot spot) {
        if (spot != null) {
            spot.setTower(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List <double[]> getDecorations(){
        return decorations;
    }
}

