package it.unibo.TowerSiege.model.buildingspot.impl;

import it.unibo.TowerSiege.commons.GameConstants;
import it.unibo.TowerSiege.model.tower.impl.TowerImpl;

public class BuildingSpot {
    private final int col;
    private final int row;
    private TowerImpl tower;

    public BuildingSpot(int col, int row){
        this.col = col;
        this.row = row;
        this.tower = null;
    }

    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    public double getPixelCenterX() {
        return (col * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
    }

    public double getPixelCenterY() {
        return (row * GameConstants.TILE_SIZE) + (GameConstants.TILE_SIZE / 2.0);
    }

    public boolean isOccupied() {
        return tower != null;
    }

    public TowerImpl getTower() {
        return tower;
    }

    public void setTower(TowerImpl tower) {
        this.tower = tower;
        if (tower != null) {
            tower.setPosition(getPixelCenterX(), getPixelCenterY());
        }
    }
}