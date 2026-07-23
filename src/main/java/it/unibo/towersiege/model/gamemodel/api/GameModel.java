package it.unibo.towersiege.model.gamemodel.api;

import java.util.List;

import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.gamemap.api.GameMap;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.model.player.api.Player;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.score.api.Score;
import it.unibo.towersiege.model.tower.api.Tower;

public interface GameModel {

    /**
     * Loads the map and configuration for the given level number.
     * 
     * @param levelNum level to load (1-based)
     */
    void loadLevel(int levelNum);

    /**
     * Starts(or restarts) the game: resets all entities and begins in PLAYING
     * state.
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
     * 
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

    /**
     * Return a copy of currently active enemies.
     * 
     * @return list of active enemies
     */
    List<Enemy> getActiveEnemies();

    /**
     * Returns a snapshot of currently active enemies.
     * 
     * @return list of active projectiles
     */
    List<Projectile> getProjectiles();

    /**
     * Returns the index of the last wave.
     * 
     * @return current wave number
     */
    int getCurrentWave();

    /**
     * Return the total wave.
     */
    int getTotalWaves();

    /**
     * Returns if a wave is in progress.
     * 
     * @return true if wave is in progress
     */
    boolean isWaveInProgress();

    /**
     * Returns the remaining ticks of fire ability.
     * 
     * @return fire cooldown ticks
     */
    int getFireCooldown();

    /**
     * Return the remaining ticks of freeze ability.
     * 
     * @return freeze cooldown ticks
     */
    int getFreezeCooldown();

    /**
     * Returns the remaining animation for the fire visual.
     * 
     * @return fire anim ticks
     */
    int getFireAnimTicks();

    /**
     * Returns the remaining animation for the freee visual.
     * 
     * @return freeze anim ticks
     */
    int getFreezeAnimTicks();

    /**
     * Return curretn game state.
     * 
     * @return current game state
     */
    GameState getState();

    /**
     * Sets the gamestate directly (used by controller for screen transitions).
     * 
     * @param state the new game state
     */
    void setState(GameState state);

    /**
     * Returns the score tracker for the current session.
     * 
     * @return score tracker
     */
    Score getScore();

    /**
     * Returns the Player
     * 
     * @return player
     */
    Player getPlayer();

    /**
     * Returns the current map.
     * 
     * @return current map
     */
    GameMap getMap();

    /**
     * Returns true when the game is VICTORY state and redirect countdown has
     * expired.
     * 
     * @return true if victory redirect ready
     */
    boolean isVictoryRedirectReady();

    /**
     * Returns the currently loaded level number.
     * 
     * @return current level number
     */
    int getCurrentLevel();

    /**
     * Returns the highest level the player has unlocked.
     * 
     * @return max unlocked level
     */
    int getMaxUnlockedLevel();
}
