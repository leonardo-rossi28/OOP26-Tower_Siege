package it.unibo.towersiege.model.projectile.impl;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.tower.TowerType;
import it.unibo.towersiege.model.tower.api.Tower;

/**
 * This class represent a projectile that moves towards the target
 * The projectile hit if the enemy is in the same pixel otherwise the
 * projectile continues to move.
 */
public final class ProjectileImpl implements Projectile {

    private static final double SPEED = 15.0;
    private static final double OFFSET = 20.0;
    private static final double SLOW_FACTOR = 0.5;
    private static final int SLOW_DURATION = 20;

    private final Tower source;
    private final Enemy target;
    private double x;
    private double y;
    private boolean alive = true;

    /**
     * Construct a new projectile fired from a source tower at a targe enemy.
     * 
     * @param source the source
     * @param target the target
     */
    public ProjectileImpl(final Tower source, final Enemy target) {
        this.source = source;
        this.target = target;
        this.x = source.getPixelX();
        this.y = source.getPixelY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (!target.isAlive()) {
            this.alive = false;
            return;
        }

        final double tx = target.getPixelX() + OFFSET;
        final double ty = target.getPixelY() + OFFSET;
        final double dx = tx - x;
        final double dy = ty - y;
        final double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= SPEED) {
            target.takeDamage(source.getDamage());
            if (source.getType() == TowerType.ICE) {
                target.applySlow(SLOW_FACTOR, SLOW_DURATION);
            }
            this.alive = false;
        } else {
            this.x += dx / dist * SPEED;
            this.y += dy / dist * SPEED;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TowerType getSourceTowerType() {
        return source.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return alive;
    }
}
