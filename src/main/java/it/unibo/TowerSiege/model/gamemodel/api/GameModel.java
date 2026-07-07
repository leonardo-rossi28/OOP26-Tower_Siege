package it.unibo.TowerSiege.model.gamemodel.api;

import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.projectile.api.Projectile;
import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.gamemap.api.GameMap;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.model.score.api.Score;
import it.unibo.TowerSiege.model.player.api.Player;

import java.util.List;

public interface GameModel {

    /**
     * Loads the map and configuration for the given level number.
     * 
     * @param levelNum level to load (1-based)
     */
    void loadLevel(int levelNum);

    /**
     * Starts(or restarts) the game: resets all entities and begins in PLAYING state.
     */
    void start();

    /**
     * Turn on the next enemy wave if no one is in progress.
     */
    void startNextWave();

    /**
     * Advance the game; spawn enemies, move them,
     * fires towers, resolves projectiles and check game conditions.
     */
    void update();

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

    /**
     * Pause the game (only from PLYING state)
     */
    void pause();

    /**
     * Resumes the game(only from PAUSED state)
     */
    void resume();

    /**Return a copy of currently active enemies.*/
    List<Enemy> getActiveEnemies();

    /** Returns a snapshot of currently active enemies. */
    List<Projectile> getProjectiles();

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

    /**
     * Returns the current map
     */
    GameMap getMap();

    /**
     * Returns true when the game is VICTORY state and redirect countdown has expired
     */
    boolean isVictoryRedirectReady();

    /**
     * Returns the currently loaded level number
     */
    int getCurrentLevel();

    /**
     * Returns the highest level the player has unlocked
     */
    int getMaxUnlockedLevel();
}
