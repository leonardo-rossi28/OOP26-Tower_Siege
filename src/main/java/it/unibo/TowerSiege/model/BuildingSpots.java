package it.unibo.TowerSiege.model;

import it.unibo.TowerSiege.commons.GameConstants;

public class BuildingSpots {
    private final int col;
    private final int row;
    private Tower tower;

    public BuildingSpots(int col, int row){
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

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
        if (tower != null) {
            tower.setPosition(getPixelCenterX(), getPixelCenterY());
        }
    }
}