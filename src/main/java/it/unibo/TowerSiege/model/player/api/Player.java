package it.unibo.TowerSiege.model.player.api;

public interface Player {
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
