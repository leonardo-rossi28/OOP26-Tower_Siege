package it.unibo.towersiege.controller.shopcontroller.api;

import it.unibo.towersiege.model.tower.TowerType;

public interface ShopController {

    /**
     * Sets a selected Tower given its type.
     * @param type the type
     */
    void setSelectedTowerType(TowerType type);
    
    /**
     * Gets the selected tower type
     * @return selectedTowerType the type
     */
    TowerType getSelectedTowerType();
}
