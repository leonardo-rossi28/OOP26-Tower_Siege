package it.unibo.TowerSiege.controller.mapcontroller.impl;

import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.controller.mapcontroller.api.MapController;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.tower.impl.TowerImpl;

public final class MapControllerImpl implements MapController{
    private final GameModel model;
    private final ShopController shopController;
    
    /**
     * MapController constructor.
     * @param model the game model
     * @param shopController the game shopcontroller
     */
    public MapControllerImpl(final GameModel model, final ShopController shopController){
        this.model = model;
        this.shopController = shopController;
    }
    
    /**
     * Interacts with a given spot
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
