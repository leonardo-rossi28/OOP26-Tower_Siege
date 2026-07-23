package it.unibo.towersiege.model.gamemap.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.towersiege.commons.gameconstants.GameConstants;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.buildingspot.impl.BuildingSpotImpl;
import it.unibo.towersiege.model.gamemap.api.GameMap;
import it.unibo.towersiege.model.tower.api.Tower;

/**
 * Represents the grid-based game map containing the enemy path.
 * building spots and placed towers.
 */

public class GameMapImpl implements GameMap {

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
     * Consturct a GameMap from the grid-coordinate data.
     * 
     * @param width            map width
     * @param height           map height
     * @param backgroundPath   background path image
     * @param pathGridCoords   [col][row] waypoints for enemy path
     * @param spotGridCoords   [col][row] list for building spots
     * @param decorationCoords list of [col,row,type] positions for decorations
     */
    public GameMapImpl(final int width, final int height, final String backgroundPath,
            final List<double[]> pathGridCoords, final List<double[]> spotGridCoords,
            final List<double[]> decorationCoords) {
        this.width = width;
        this.height = height;
        this.backgroundPath = backgroundPath != null ? backgroundPath : "";

        this.grid = new int[GameConstants.ROWS][GameConstants.COLS];
        this.pixelWaypoints = new ArrayList<>();
        this.buildingSpots = new ArrayList<>();
        this.decorations = decorationCoords != null ? new ArrayList<>(decorationCoords) : new ArrayList<>();

        if (pathGridCoords != null) {
            for (final double[] coord : pathGridCoords) {
                final int col = (int) coord[0];
                final int row = (int) coord[1];

                if (col >= 0 && col < GameConstants.COLS && row >= 0 && row < GameConstants.ROWS) {
                    grid[row][col] = 1;
                    pixelWaypoints.add(new double[] {
                            (col * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0),
                            (row * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0),
                    });
                }
            }
        }

        if (spotGridCoords != null) {
            for (final double[] coord : spotGridCoords) {
                final int col = (int) coord[0];
                final int row = (int) coord[1];

                if (col >= 0 && col < GameConstants.COLS && row >= 0 && row < GameConstants.ROWS) {
                    grid[row][col] = 2;
                    buildingSpots.add(new BuildingSpotImpl(col, row));
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /** {@inheritDoc} */
    @Override
    public String getBackgroundPath() {
        return backgroundPath;
    }

    /** {@inheritDoc} */
    @Override
    public List<double[]> getWaypoints() {
        return pixelWaypoints;
    }

    /** {@inheritDoc} */
    @Override
    public List<BuildingSpot> getBuildingSpots() {
        return buildingSpots;
    }

    /** {@inheritDoc} */
    @Override
    public int[][] getGrid() {
        final int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tower> getTowers() {
        return buildingSpots.stream()
                .filter((final BuildingSpot spot) -> spot.isOccupied())
                .map((final BuildingSpot spot) -> spot.getTower())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildingSpot getSpotAt(final int col, final int row) {
        if (col < 0 || col >= GameConstants.COLS || row < 0 || row >= GameConstants.ROWS) {
            return null;
        }
        for (final BuildingSpot spot : buildingSpots) {
            if (spot.getCol() == col && spot.getRow() == row) {
                return spot;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addTowerToSpot(final Tower tower, final BuildingSpot spot) {
        if (!spot.isOccupied()) {
            spot.setTower(tower);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
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
    public List<double[]> getDecorations() {
        return decorations;
    }
}
