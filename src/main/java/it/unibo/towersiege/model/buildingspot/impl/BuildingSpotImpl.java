package it.unibo.towersiege.model.buildingspot.impl;

import it.unibo.towersiege.commons.gameconstants.GameConstants;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.tower.api.Tower;

/**
 * Implementation of the BuildingSpot interface.
 */
public class BuildingSpotImpl implements BuildingSpot {
    private final int col;
    private final int row;
    private Tower tower;

    /**
     * Creates a new BuildingSpot at the given coordinate.
     * 
     * @param col the column coordinate.
     * @param row the row coordinate.
     */
    public BuildingSpotImpl(final int col, final int row) {
        this.col = col;
        this.row = row;
        this.tower = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPixelCenterX() {
        return (col * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPixelCenterY() {
        return (row * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOccupied() {
        return tower != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tower getTower() {
        return tower;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTower(final Tower tower) {
        this.tower = tower;
        if (tower != null) {
            tower.setPosition(getPixelCenterX(), getPixelCenterY());
        }
    }
}
