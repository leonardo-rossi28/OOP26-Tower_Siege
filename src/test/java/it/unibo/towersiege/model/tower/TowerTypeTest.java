package it.unibo.towersiege.model.tower;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TowerTypeTest {

    private static final int BASIC_COST = 50;
    private static final int BASIC_DMG = 5;
    private static final int BASIC_RANGE = 3;
    private static final int BASIC_CD = 60;
    private static final int SNIPER_COST = 120;
    private static final int SNIPER_DMG = 15;
    private static final int SNIPER_RANGE = 5;
    private static final int SNIPER_CD = 120;

    @Test
    void testTowerTypeValues() {
        assertEquals(BASIC_COST, TowerType.BASIC.getCost());
        assertEquals(BASIC_DMG, TowerType.BASIC.getDamage());
        assertEquals(BASIC_RANGE, TowerType.BASIC.getRange());
        assertEquals(BASIC_CD, TowerType.BASIC.getCooldown());
        assertEquals("Torre Cristallo", TowerType.BASIC.getDescription());

        assertEquals(SNIPER_COST, TowerType.SNIPER.getCost());
        assertEquals(SNIPER_DMG, TowerType.SNIPER.getDamage());
        assertEquals(SNIPER_RANGE, TowerType.SNIPER.getRange());
        assertEquals(SNIPER_CD, TowerType.SNIPER.getCooldown());
    }
}
