package it.unibo.towersiege.controller.shopcontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.controller.shopcontroller.api.ShopController;
import it.unibo.towersiege.controller.shopcontroller.impl.ShopControllerImpl;
import it.unibo.towersiege.model.tower.TowerType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopControllerImplTest {

    private ShopController shopController;

    @BeforeEach
    void setUp() {
        shopController = new ShopControllerImpl();
    }

    @Test
    void testInitialState() {
        assertEquals(TowerType.BASIC, shopController.getSelectedTowerType());
    }

    @Test
    void testSelectedTowerType() {
        shopController.setSelectedTowerType(TowerType.SNIPER);
        assertEquals(TowerType.SNIPER, shopController.getSelectedTowerType());

        shopController.setSelectedTowerType(TowerType.ICE);
        assertEquals(TowerType.ICE, shopController.getSelectedTowerType());

        // Null should not overwrite current selection
        shopController.setSelectedTowerType(null);
        assertEquals(TowerType.ICE, shopController.getSelectedTowerType());
    }
}
