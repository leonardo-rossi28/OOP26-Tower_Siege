package it.unibo.towersiege.model.player.api;

public interface Player {

    /**
     * Updates the player's balance by adding or subtracting the specified amount.
     *
     * @param amount the number of coins to add (positive) or deduct (negative)
     */
    void addCoins(int amount);

    /**
     * Modifies the player's wallet balance by adding or deducting the specified amount of coins.
     * 
     * @param amount the amount of coins to spend
     * @return true if the coins were successfully spent, false if there are insufficient funds
    */
    boolean spendCoins(int amount);

    /**
     * Reduces the bases health by the specified damage amount.
     * 
     * @param damage the amount of damage taken by the base
     */
    void takeBaseDamage(int damage);

    /**
    * Resets base health and coins to the starting value.
    *
    * @return the current base health
    */
    void reset();

    /**
     * Returns the current amount of coins the player possesses.
     * 
     * @return the number of coins
     */
    int getCoins();

    /**
     * Returns the current health of the player's base.
     * 
     * @return the current base health
     */
    int getBaseHealth();

    /**
     * Check if the player base is still alive
     * 
     * @return true if the base health is greater than zero , false otherwise
     */
    boolean isBaseAlive();
}