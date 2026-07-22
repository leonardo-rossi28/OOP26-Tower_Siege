package it.unibo.towersiege.model.tower;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TowerTypeTest {

    @Test
    void testTowerTypeValues() {
        assertEquals(50, TowerType.BASIC.getCost());
        assertEquals(5, TowerType.BASIC.getDamage());
        assertEquals(3, TowerType.BASIC.getRange());
        assertEquals(60, TowerType.BASIC.getCooldown());
        assertEquals("Torre Cristallo", TowerType.BASIC.getDescription());

        assertEquals(120, TowerType.SNIPER.getCost());
        assertEquals(15, TowerType.SNIPER.getDamage());
        assertEquals(5, TowerType.SNIPER.getRange());
        assertEquals(120, TowerType.SNIPER.getCooldown());
    }
}
