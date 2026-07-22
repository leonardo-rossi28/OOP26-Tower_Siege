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

    private static final int COINS_TO_ADD = 50;
    private static final int COINS_TO_SPEND = 30;
    private static final int COINS_REMAINING = 20;
    private static final int COINS_TOO_MANY = 1000;
    private static final int COINS_TO_SPEND_RESET = 100;
    private static final int DAMAGE_1 = 50;
    private static final int DAMAGE_FATAL = 100;

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
        player.addCoins(COINS_TO_ADD);
        assertEquals(GameConstants.STARTING_COINS + COINS_TO_ADD, player.getCoins());

        assertTrue(player.spendCoins(COINS_TO_SPEND));
        assertEquals(GameConstants.STARTING_COINS + COINS_REMAINING, player.getCoins());

        assertFalse(player.spendCoins(COINS_TOO_MANY));
        assertEquals(GameConstants.STARTING_COINS + COINS_REMAINING, player.getCoins());
    }

    @Test
    void testTakeBaseDamage() {
        player.takeBaseDamage(DAMAGE_1);
        assertEquals(GameConstants.BASE_HEALTH - DAMAGE_1, player.getBaseHealth());
        assertTrue(player.isBaseAlive());

        player.takeBaseDamage(DAMAGE_FATAL);
        assertFalse(player.isBaseAlive());
    }

    @Test
    void testReset() {
        player.spendCoins(COINS_TO_SPEND_RESET);
        player.takeBaseDamage(DAMAGE_1);
        player.reset();

        assertEquals(GameConstants.STARTING_COINS, player.getCoins());
        assertEquals(GameConstants.BASE_HEALTH, player.getBaseHealth());
        assertTrue(player.isBaseAlive());
    }
}
