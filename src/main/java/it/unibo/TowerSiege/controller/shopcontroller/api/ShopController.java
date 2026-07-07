package it.unibo.TowerSiege.controller.api

import it.unibo.TowerSiege.model.tower.TowerType

public interface ShopController {
    void setSelectedTowerType(TowerType type);
    TowerType getSelectedTowerType();
}
