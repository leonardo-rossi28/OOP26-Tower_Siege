package it.unibo.TowerSiege.controller.mapcontroller.impl;

import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.controller.mapcontroller.api.MapController;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.tower.impl.TowerImpl;

public class MapControllerImpl implements MapController{
    private final GameModel model;
    private final ShopController shopController;
    
    public MapControllerImpl(GameModel model, ShopController shopController){
        this.model = model;
        this.shopController = shopController;
    }
    
    @Override
    public void interactWithSpot(BuildingSpot spot){
        if (model.getState() != GameState.PLAYING || spot == null) { return; }
        if (!spot.isOccupied()) {
            Tower t = new TowerImpl(shopController.getSelectedTowerType());
            model.buildTowerOnSpot(t, spot);
        }  
        else
        {
            model.upgradeTower(spot.getTower());
        }
    }

    @Override
    public void sellTowerAtSpot(BuildingSpot spot){
        if (model.getState() != GameState.PLAYING || spot == null) { return; }
        if (spot.isOccupied()) {
            model.sellTower(spot);
        }
    }
}
