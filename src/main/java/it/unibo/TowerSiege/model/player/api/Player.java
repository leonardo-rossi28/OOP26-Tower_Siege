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

    /**
     * 
     * TDrops down the base health according with the given damage amount
     * @param damage
     */

    void takeBaseDamage(int damage);

    /**
    * Resets base health and coins to the starting value
    */
    void reset();


    int getCoins();

    /**
     * Returns the current health of the player's base
     * @return
     */

    int getBaseHealth();

    boolean isBaseAlive();
}