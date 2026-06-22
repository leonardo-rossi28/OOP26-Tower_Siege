package it.unibo.TowerSiege.model.buildingspot.impl;

import it.unibo.TowerSiege.commons.gameconstants.GameConstants;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.tower.api.Tower;

public class BuildingSpotImpl implements BuildingSpot{
    private final int col;
    private final int row;
    private Tower tower;

    /**
     * Creates a new BuildingSpot at the given coordinates
     * @param col
     * @param row
     */
    public BuildingSpotImpl(int col, int row){
        this.col = col;
        this.row = row;
        this.tower = null;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public int getCol() {
        return col;
    }

     /**
     * {@inheritDOc}
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public double getPixelCenterX() {
        return (col * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public double getPixelCenterY() {
        return (row * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public boolean isOccupied() {
        return tower != null;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public Tower getTower() {
        return tower;
    }

    /**
     * {@inheritDOc}
     */
    @Override
    public void setTower(Tower tower) {
        this.tower = tower;
        if (tower != null) {
            tower.setPosition(getPixelCenterX(), getPixelCenterY());
        }
    }
}