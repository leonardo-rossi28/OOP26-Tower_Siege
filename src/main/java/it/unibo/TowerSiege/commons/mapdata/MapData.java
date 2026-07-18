package it.unibo.TowerSiege.commons.mapdata;

import java.util.*;

public class MapData {
    private int width;
    private int height;
    private String background;
    private List<double[]> waypoints = new ArrayList<>();
    private List<double[]> buildingSpots = new ArrayList<>();
    private List<double[]> decorations = new ArrayList<>();

    public MapData() {}
    public int getWidth() {
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }
    public void setheight(int height){
        this.height = height;
    }

    public String getBackground(){
        return background;
    }
    public void setBackground(String background){
        this.background = background;
    }

    public List<double[]> getWaypoints() {
        return waypoints;
    }
    public void setWaypoints(List<double[]> waypoints){
        this.waypoints = waypoints;
    }

    public List<double[]> getBuildingSpots() {
        return buildingSpots;
    }
    public void setBuildingSpots(List<double[]> buildingSpots){
        this.buildingSpots = buildingSpots;
    }

    public List<double[]> getDecorations(){ 
        return decorations;
    }
    public void setDecorations(List<double[]> decorations){
        this.decorations=decorations;
    }
}
