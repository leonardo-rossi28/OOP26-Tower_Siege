package it.unibo.TowerSiege.model.gamemodel.api;

import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.projectile.api.Projectile;
import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.model.score.api.Score;
import it.unibo.TowerSiege.model.player.api.Player;

import it.unibo.TowerSiege.model.gamemap.api.GameMap;

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

    /**
     * Returns the index of the last wave.
     */
    int getCurrentWave();

    /**
     * Return the total wave.
     */
    int getTotalWaves();

    /**
     * Returns if a wave is in progress.
     */
    boolean isWaveInProgress();

    /**
     * Returns the remaining ticks of fire ability. 
     */
    int getFireCooldown();

    /**
     * Return the remaining ticks of freeze ability.
     */
    int getFreezeCooldown();

    /**
     * Returns the remaining animation for the fire visual.
     */
    int getFireAnimTicks();

    /**
     * Returns the remaining animation for the freee visual.
     */
    int getFreezeAnimTicks();

    /**
     * Starts(or restarts) the game: resets all entities and begins in PLAYING state.
     */
    void start();

    /**
     * Pause the game (only from PLYING state)
     */
    void pause();

    /**
     * Resumes the game(only from PAUSED state)
     */
    void resume();

    /**
     * Return curretn game state
     */
    GameState getState();

    /**
     * Sets the gamestate directly
     */
    void setState(GameState state);

    /**
     * Returns the score tracker for the current session
     */
    Score getScore();

    /**
     * Returns the Player
     */
    Player getPlayer();

}
