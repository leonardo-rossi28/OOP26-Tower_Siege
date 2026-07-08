package it.unibo.TowerSiege.controller.shopcontroller.impl;

import it.unibo.TowerSiege.controller.shopcontroller.api.ShopController;
import it.unibo.TowerSiege.model.tower.TowerType;

public class ShopControllerImpl implements ShopController {
    private TowerType selectedTowerType=TowerType.BASIC;

    @Override
    public void setSelectedTowerType(TowerType type){
        if(type != null){
            this.selectedTowerType=type;
        }
    }

    @Override
    public TowerType getSelectedTowerType(){
        return selectedTowerType;
    }

}
