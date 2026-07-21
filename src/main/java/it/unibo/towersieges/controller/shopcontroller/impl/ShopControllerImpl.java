package it.unibo.towersieges.controller.shopcontroller.impl;

import it.unibo.towersieges.controller.shopcontroller.api.ShopController;
import it.unibo.towersieges.model.tower.TowerType;

public final class ShopControllerImpl implements ShopController {
    private TowerType selectedTowerType=TowerType.BASIC;

    /**
     * Sets the selected Tower given its type.
     * @param type
     */
    @Override
    public void setSelectedTowerType(TowerType type){
        if(type != null){
            this.selectedTowerType=type;
        }
    }

    /**
     * Gets the selected tower type.
     * @return selectedTowerType
     */
    @Override
    public TowerType getSelectedTowerType(){
        return selectedTowerType;
    }
}
