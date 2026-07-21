package it.unibo.towersiege.controller.mapcontroller.impl;

import it.unibo.towersiege.controller.mapcontroller.api.MapController;
import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.model.tower.impl.TowerImpl;

/** Implementation of the map controller. */
public final class MapControllerImpl implements MapController{
    private final GameModel model;
    private final ShopController shopController;
    
    /**
     * Constructs a new MapControllerImpl.
     * 
     * @param model the game model
     * @param shopController the gshop controller
     */
    public MapControllerImpl(final GameModel model, final ShopController shopController){
        this.model = model;
        this.shopController = shopController;
    }
    
    /**
     * Interacts with a given spot.
     * 
     * @param spot the spot
     */
    @Override
    public void interactWithSpot(final BuildingSpot spot){
        if (model.getState() != GameState.PLAYING || spot == null) { 
            return; 
        }
        if (!spot.isOccupied()) {
            final Tower t = new TowerImpl(shopController.getSelectedTowerType());
            model.buildTowerOnSpot(t, spot);
        }  
        else
        {
            model.upgradeTower(spot.getTower());
        }
    }

    /**
     * Sells a tower in a given spot.
     * 
     * @param spot the spot
     */
    @Override
    public void sellTowerAtSpot(final BuildingSpot spot){
        if (model.getState() != GameState.PLAYING || spot == null) {
            return;
        }
        if (spot.isOccupied()) {
            model.sellTower(spot);
        }
    }
}
