package it.unibo.TowerSiege.model.player.api;

public interface Player {

    /**
     * Updates the player's balance by adding or subtracting the specified amount.
     *
     * @param amount the number of coins to add (positive) or deduct (negative)
     */


    void addCoins(int amount);

    /*
     *Modifies the player's wallet balance by adding or deducting the specified amount of coins
     */

    boolean spendCoins(int amount);

    void takeBaseDamage(int damage);

    void reset();

    int getCoins();

    int getBaseHealth();

    boolean isBaseAlive();
}