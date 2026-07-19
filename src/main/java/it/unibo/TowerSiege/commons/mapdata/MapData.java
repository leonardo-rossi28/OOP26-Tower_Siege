package it.unibo.TowerSiege.commons.mapdata;

import java.util.*;

public final class MapData {
    private int width;
    private int height;
    private String background;
    private List<double[]> waypoints = new ArrayList<>();
    private List<double[]> buildingSpots = new ArrayList<>();
    private List<double[]> decorations = new ArrayList<>();

    /**
     * Constructor for MapData
     */
    public MapData() {}

    /**
     * Gets the width
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width
     * @param width
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     * Gets the height
     * @return
     */
    public int getHeight(){
        return height;
    }

    /**
     * Sets the height
     * @param height
     */
    public void setheight(int height){
        this.height = height;
    }


    /**
     * Gets the background
     * @return the background
     */
    public String getBackground(){
        return background;
    }

    /**
     * Sets the background
     * @param background
     */
    public void setBackground(String background){
        this.background = background;
    }

    /**
     * Gets the Waypoints 
     * @return Waipoints
     */
    public List<double[]> getWaypoints() {
        return waypoints;
    }

    /**
     * Sets the Waypoints
     * @param waypoints
     */
    public void setWaypoints(List<double[]> waypoints){
        this.waypoints = waypoints;
    }

    /**
     * Gets he building spots.
     * @return the building spots
     */
    public List<double[]> getBuildingSpots() {
        return buildingSpots;
    }

    /**
     * Sets the building spots
     * @param buildingSpots
     */
    public void setBuildingSpots(List<double[]> buildingSpots){
        this.buildingSpots = buildingSpots;
    }

    /**
     * Gets the decorations
     * @return the decorations
     */
    public List<double[]> getDecorations(){ 
        return decorations;
    }

    /**
     * Sets the decorations
     * @param decorations
     */
    public void setDecorations(List<double[]> decorations){
        this.decorations=decorations;
    }
}
