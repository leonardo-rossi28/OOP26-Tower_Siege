package it.unibo.towersiege.model.buildingspot.api;

import it.unibo.towersiege.model.tower.api.Tower;

public interface BuildingSpot {
    
    /** 
     * Return grid column of this spot
     *
     *  @return column index
     */
    int getCol();

    /**
     * Return grid row of this spot
    *
    * @return  row index
    */
   int getRow();

   /**
    *  Return the X pixel of the center of the spot
    * 
    * @return pixel X center
    */
   double getPixelCenterX();

   /**
    *  Return the Y pixel of the center of the spot
    * 
    * @return pixel Y center
    */
   double getPixelCenterY();

   /** 
    * Return if tower is already occupied
    * 
    * @return true if occupied
    */
    boolean isOccupied();

    /** 
     * Return the tower already in this spot, or null if empty
     *
     *  @return placed tower, or null
     */
    Tower getTower();

    /** 
     * Places a tower on this spot
     * and updates pixel position.
     * 
     * @param  tower the tower to place, or null to clear
    */
   void setTower(Tower tower);

}
