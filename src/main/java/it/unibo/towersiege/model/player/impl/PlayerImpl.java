package it.unibo.towersiege.model.player.impl;

import it.unibo.towersiege.commons.gameconstants.GameConstants;
import it.unibo.towersiege.model.player.api.Player;

/**
 * Represents the player state: coins and base health.
 */
public final class PlayerImpl implements Player {

    private int coins;
    private int baseHealth;

    /**
     * New PlayerImpl and resets to starting values.
     */
    public PlayerImpl() {
        reset();
    }

    @Override
    public void reset() {
        this.coins = GameConstants.STARTING_COINS;
        this.baseHealth = GameConstants.BASE_HEALTH;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void addCoins(final int amount) {
        this.coins += amount;
    }

    @Override
    public boolean spendCoins(final int amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        }

        return false;
    }

    @Override
    public void takeBaseDamage(final int damage) {
        this.baseHealth -= damage;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public int getBaseHealth() {
        return baseHealth;

    }

    @Override
    public boolean isBaseAlive() {

        return baseHealth > 0;
    }
}
