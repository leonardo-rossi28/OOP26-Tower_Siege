package it.unibo.TowerSiege.model.enemy.api;

import it.unibo.TowerSiege.model.enemy.EnemyType;
import java.util.List;

public interface Enemy {
    
    /**
     * @param damage amount of damage to inflict
     */
    void takeDamage(int damage);

    /**
     * @param multiplier speed multiplier
     * @param durationTicks number of game ticks the slow lasts
     */
    void applySlow(double multiplier, int durationTicks);

    /**
     * Update enemy state
     */
    void updateStatus();

    void tickVisuals();

    /**
     * @param waypoints coordinate waypoints
     * @return true if the enemy arrive at the end
     */
    boolean moveAlongPath(List<double[]> waypoints);

    /**
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    void setPosition(double x, double y);

    /**
     * @param val true if reached
     */
    void setReachedEnd(boolean val);

    /**
     * @param val true if coin is award
     */
    void setCoinAwarded(boolean val);

    /**
     * @return enemy type
     */
    EnemyType getType();

    /**
     * @return max health
     */
    int getMaxHealth();

    /**
     * @return current health
     */
    int getHealth();

    /**
     * @return reward value
     */
    int getReward();

    /**
     * @return true if alive
     */
    boolean isAlive();

    /**
     * @return pixel X
     */
    double getPixelX();

    /**
     * @return pixel Y
     */
    double getPixelY();

    /**
     * @return hit flash ticks
     */
    int getHitFlashTicks();

    /**
     * @return true if reach end
     */
    boolean isReachedEnd();

    /**
     * @return true if coin is awarded
     */
    boolean isCoinAwarded();
}
