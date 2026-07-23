package it.unibo.towersiege.model.tower.impl;

import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.projectile.impl.ProjectileImpl;
import it.unibo.towersiege.model.tower.TowerType;
import it.unibo.towersiege.model.tower.api.Tower;

/**
 * Implementation of the Tower interface.
 * Represent a physical tower palcedd in the map.
 */
public class TowerImpl implements Tower {

    private static final int PIXEL_PER_CELL = 40;

    private final TowerType type;
    private double pixelX;
    private double pixelY;
    private int level;
    private final boolean alive;
    private int cooldownTicks; 

    /**
     * Constructs a new tower with the specified type.
     * 
     * @param type the tower type.
     */
    public TowerImpl(final TowerType type) {
        this.type = type;
        this.level = 1;
        this.alive = true;
        this.cooldownTicks = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final double x, final double y) {
        this.pixelX = x;
        this.pixelY = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick() {
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnemyInRange(final Enemy enemy) {
        final double dx = enemy.getPixelX() - pixelX;
        final double dy = enemy.getPixelY() - pixelY;
        final double distSq = dx * dx + dy * dy;
        final double rangePx = type.getRange() * PIXEL_PER_CELL;
        return distSq <= (rangePx * rangePx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upgrade() {
        this.level++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TowerType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPixelX() {
        return pixelX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPixelY() {
        return pixelY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return alive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRange() {
        return type.getRange();
    }

    /**
     * {@inheritDoc}
     */
    @Override
     public int getDamage() {
        return type.getDamage() + (level - 1) * 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Projectile attack(final Enemy enemy) {
        if (!alive || cooldownTicks > 0) {
            return null;
        }
        if (isEnemyInRange(enemy)) {
            cooldownTicks = type.getCooldown();
            return new ProjectileImpl(this, enemy);
        }
        return null;
    }
}
