package it.unibo.towersiege.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.commons.gameconstants.GameConstants;
import it.unibo.towersiege.model.player.api.Player;
import it.unibo.towersiege.model.player.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlayerImplTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new PlayerImpl();
    }

    @Test
    void testInitialState() {
        assertEquals(GameConstants.STARTING_COINS, player.getCoins());
        assertEquals(GameConstants.BASE_HEALTH, player.getBaseHealth());
        assertTrue(player.isBaseAlive());
    }

    @Test
    void testAddAndSpendCoins() {
        player.addCoins(50);
        assertEquals(GameConstants.STARTING_COINS + 50, player.getCoins());

        assertTrue(player.spendCoins(30));
        assertEquals(GameConstants.STARTING_COINS + 20, player.getCoins());

        assertFalse(player.spendCoins(0));
        assertEquals(GameConstants.STARTING_COINS + 20, player.getCoins());
    }

    @Test
    void testTakeBaseDamage() {
        player.takeBaseDamage(50);
        assertEquals(GameConstants.BASE_HEALTH - 50, player.getBaseHealth());
        assertTrue(player.isBaseAlive());

        player.takeBaseDamage(100);
        assertFalse(player.isBaseAlive());
    }

    @Test
    void testReset() {
        player.spendCoins(100);
        player.takeBaseDamage(50);
        player.reset();

        assertEquals(GameConstants.STARTING_COINS, player.getCoins());
        assertEquals(GameConstants.BASE_HEALTH, player.getBaseHealth());
        assertTrue(player.isBaseAlive());
    }
}
