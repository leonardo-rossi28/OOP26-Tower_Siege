package it.unibo.TowerSiege.commons.mapdata;

import java.util.*;

/**
 * MapData
 */
public final class MapData {
    
    private int width;
    private int height;
    private String background;
    private List<double[]> waypoints = new ArrayList<>();
    private List<double[]> buildingSpots = new ArrayList<>();
    private List<double[]> decorations = new ArrayList<>();

    /**
     * Constructor for MapData.
     */
    public MapData() { }

    /**
     * Gets the width.
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width.
     * @param width the width
     */
    public void setWidth(final int width){
        this.width = width;
    }

    /**
     * Gets the height.
     * @return the height 
     */
    public int getHeight(){
        return height;
    }

    /**
     * Sets the height.
     * @param height the height
     */
    public void setheight(final int height){
        this.height = height;
    }

    /**
     * Gets the background.
     * @return the background
     */
    public String getBackground(){
        return background;
    }

    /**
     * Sets the background.
     * @param background the backgound
     */
    public void setBackground(final String background){
        this.background = background;
    }

    /**
     * Gets the waypoints.
     * @return the waypoints
     */
    public List<double[]> getWaypoints() {
        return waypoints;
    }

    /**
     * Sets the waypoints.
     * @param waypoints the waypoints
     */
    public void setWaypoints(final List<double[]> waypoints){
        this.waypoints = waypoints;
    }

    /**
     * Gets the building spots.
     * @return the building spots
     */
    public List<double[]> getBuildingSpots() {
        return buildingSpots;
    }

    /**
     * Sets the building spots.
     * @param buildingSpots the building spots
     */
    public void setBuildingSpots(final List<double[]> buildingSpots){
        this.buildingSpots = buildingSpots;
    }

    /**
     * Gets the decorations.
     * @return the decorations
     */
    public List<double[]> getDecorations(){ 
        return decorations;
    }

    /**
     * Sets the decoration.
     * @param decorations the decorations
     */
    public void setDecorations(final List<double[]> decorations){
        this.decorations=decorations;
    }
}
