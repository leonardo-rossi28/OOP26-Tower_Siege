package it.unibo.TowerSiege.model.score.impl;
import it.unibo.TowerSiege.model.score.api.Score;

/**
 * Defines the contract for managinng the player's current score
 * Increments points whenever an enemy is defeated.
 */


public final class ScoreImpl implements Score {

    private int total;

    /** Creates a new ScoreImpl starting at zero.  */
    public ScoreImpl() {
        this.total = 0;
    }


    /**
     * {@inheritDoc} 
     */


    @Override
    public void addPoints(final int points) {
        if (points > 0) {
            this.total += points;

        }
    }

    @Override
    public int getTotal() {
        return total;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void reset() {
        this.total = 0;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "Score: " + total;
    }

}