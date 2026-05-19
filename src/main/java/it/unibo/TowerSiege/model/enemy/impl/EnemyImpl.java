package it.unibo.TowerSiege.model.enemy.impl;

import java.util.List;
import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.enemy.EnemyType;

public class EnemyImpl implements Enemy {
    
    private final EnemyType type;
    private int maxHealth;
    private int health;
    private double baseSpeed;
    private int reward;

    private double pixelX;
    private double pixelY;
    private int currentWaypointIndex;

    private boolean alive;
    private int slowDurationTicks;
    private double slowMultiplier;
    private int hitFlashTicks;
    private boolean reachedEnd;
    private boolean coinAwarded;

    /**
     * 
     * @param type enemy type
     * @param waveNumber number of the current wave
     */
    public EnemyImpl(EnemyType type, int waveNumber) {
        this.type = type;
        this.maxHealth = type.getHealth() + (waveNumber *8);
        this.health = this.maxHealth;
        this.baseSpeed = type.getSpeed() * 1.0;
        this.reward = type.getReward();
        this.alive = true;
        this.slowDurationTicks = 0;
        this.slowMultiplier = 1.0;
        this.hitFlashTicks = 0;
        this.currentWaypointIndex = 1;
        this.reachedEnd = false;
        this.coinAwarded = false;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void takeDamage(final int damage) {
        this.health -= damage;
        this.hitFlashTicks = 8;
        if(this.health <= 0) {
            this.health=0;
            this.alive = false;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void setReachedEnd(final boolean val) {
        this.reachedEnd = val;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isReachedEnd() {
        return reachedEnd;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void setCoinAwarded(final boolean val) {
        this.coinAwarded=val;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isCoinAwarded() {
        return coinAwarded;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void tickVisuals() {
        if( hitFlashTicks > 0) {
            hitFlashTicks--;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int getHitFlashTicks() {
        return hitFlashTicks;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void applySlow(final double multiplier, final int durationTicks) {
        this.slowMultiplier = multiplier;
        this.slowDurationTicks = durationTicks;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void updateStatus() {
        if(slowDurationTicks > 0) {
            slowDurationTicks--;
            if (slowDurationTicks == 0) slowMultiplier = 1.0;
        }
    }

    /**
     * 
     * @return speed for ticks
     */
    public double getEffectiveSpeed() {
        return baseSpeed * slowMultiplier;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean moveAlongPath(final List<double[]> waypoints) {
        if(waypoints == null || waypoints.isEmpty() || currentWaypointIndex >= waypoints.size()) {
            return true;
        }
        final double[] target = waypoints.get(currentWaypointIndex);
        final double dx = target[0] - pixelX;
        final double dy = target[1] - pixelY;
        final double dist = Math.sqrt(dx*dx + dy*dy);
        final double step = getEffectiveSpeed();

        if(dist <= step) {
            pixelX = target[0];
            pixelY = target[1];
            currentWaypointIndex++;
            return currentWaypointIndex >= waypoints.size();
        } else {
            pixelX += (dx/dist) * step;
            pixelY += (dy/dist) * step;
        }
        return false;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public EnemyType getType() { return type; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int getMaxHealth() { return maxHealth; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int getHealth() { return health; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int getReward() { return reward; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() { return alive; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public double getPixelX() { return pixelX; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public double getPixelY() { return pixelY; }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void setPosition(double x,double y) {
        this.pixelX = x;
        this.pixelY = y;
    }
}
