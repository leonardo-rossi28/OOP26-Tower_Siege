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
}
