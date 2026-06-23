package it.unibo.TowerSiege.model.gamemodel.api;

import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.projectile.api.Projectile;
import it.unibo.TowerSiege.model.enemy.api.Enemy;

import java.util.List;

public interface GameModel {

    /**
     * Attempts to build the given tower on the given spot.
     * Deducts cost from player coins on success.
     * 
     * @param tower
     * @param spot
     * @return true if the tower was placed successfully
     */
    boolean buildTowerOnSpot(Tower tower, BuildingSpot spot);

    /**
     * Attempts to upgrade the given tower by one level.
     * Deducts upgrade cost from player coins on success.
     * @param tower tower to upgrade
     * @return ture if upgraded successfully
     */
    boolean upgradeTower(Tower tower);

    /**
     * Sells the tower on the given spot, refunding half its cost.
     * 
     * @param spot spot whose tower is sold
     * @return true if sold successfully
     */
    boolean sellTower(BuildingSpot spot);

    /** Returns a snapshot of currently active enemies. */
    List<Projectile> getProjectiles();

    /**
     * Turn on the next enemy wave if no one is in progress.
     */
    void startNextWave();

    /**
     * Activate the rain of fire ability.
     * Only in the playing state.
     */
    void castRainOfFire();

    /**
     * Activate the global freeze ability.
     * Only in the playing state.
     */
    void castGlobalFreeze();

    /**Return a copy of currently active enemies.*/
    List<Enemy> getActiveEnemies();

    /**
     * Advance the game; spawn enemies, move them,
     * fires towers, resolves projectiles and check game conditions.
     */
    void update();
}
