package it.unibo.towersiege.controller.shopcontroller.impl;

import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.model.tower.TowerType;

/** Implementation of the shop controller. */
public final class ShopControllerImpl implements ShopController {
    private TowerType selectedTowerType = TowerType.BASIC;

    /** Constructs a new ShopControllerImpl. */
    public ShopControllerImpl() {
        // Default constructror
    }

    /**
     * Sets the selected Tower given its type.
     * 
     * {@inheritDoc}
     * 
     * @param type type the type of tower to select.
     */
    @Override
    public void setSelectedTowerType(final TowerType type) {
        if (type != null) {
            this.selectedTowerType = type;
        }
    }

    /**
     * Gets the selected tower type.
     * 
     * {@inheritDoc}
     * 
     * @return selectedTowerType
     */
    @Override
    public TowerType getSelectedTowerType() {
        return selectedTowerType;
    }
}
