package it.unibo.TowerSiege.model.score.api;

/**
 * Defines the contract for managinng the player's current score
 * Increments points whenever an enemy is defeated.
 */

public interface Score {
    /** 
     * Adds points to the total score.
     * 
     * @param points the number of points to add (must be positive)
     */

    void addPoints(int points);

    /**
     * Returns the current total score
     * 
     * @return total score
     */

    int getTotal();

    /**
     * Resets the score to zero
     */

    void reset();
}