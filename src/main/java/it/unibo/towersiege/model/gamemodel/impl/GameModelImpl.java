package it.unibo.towersiege.model.gamemodel.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.towersiege.commons.mapdata.MapData;
import it.unibo.towersiege.commons.maploader.MapLoader;
import it.unibo.towersiege.commons.savemanager.SaveManager;
import it.unibo.towersiege.commons.soundmanager.SoundManager;
import it.unibo.towersiege.model.buildingspot.api.BuildingSpot;
import it.unibo.towersiege.model.enemy.api.Enemy;
import it.unibo.towersiege.model.gamemap.api.GameMap;
import it.unibo.towersiege.model.gamemap.impl.GameMapImpl;
import it.unibo.towersiege.model.gamemodel.api.GameModel;
import it.unibo.towersiege.model.gamestate.GameState;
import it.unibo.towersiege.model.player.api.Player;
import it.unibo.towersiege.model.player.impl.PlayerImpl;
import it.unibo.towersiege.model.projectile.api.Projectile;
import it.unibo.towersiege.model.score.api.Score;
import it.unibo.towersiege.model.score.impl.ScoreImpl;
import it.unibo.towersiege.model.tower.api.Tower;
import it.unibo.towersiege.model.wave.api.Wave;
import it.unibo.towersiege.model.wave.impl.WaveImpl;

public class GameModelImpl implements GameModel {

    private static final int BASE_DAMAGE_PER_ENEMY = 10;
    private static final int SPAWN_DELAY_TICKS = 60; //1 second at 60fps
    private static final int FIRE_COOLDOWN = 900; //15 seconds
    private static final int FREEZE_COOLDOWN = 480; // 8 SECONDS
    private static final int DEFAULT_MAP_WIDTH = 800;
    private static final int DEFAULT_MAP_HEIGHT = 600;
    private static final int DEFAULT_WAYPOINT_Y = 300;
    private static final int INITIAL_SPAWN_COOLDOWN = 30;
    private static final int FATAL_DAMAGE = 999;
    private static final int FIRE_RAIN_DAMAGE = 50;
    private static final int ANIMATION_DURATION = 60;
    private static final int FREEZE_DURATION = 180;
    private static final double FREEZE_SLOW_MULTIPLIER = 0.3;
    private static final int VICTORY_DELAY_TICKS = 50;

    private GameState state;
    private GameMap map;
    private final Player player;
    private final Wave wave;

    private final List<Enemy> activeEnemies;
    private final List<Enemy> spawnQueue;
    private final List<Projectile> projectiles;

    private int  spawnCooldownTicks;
    private int currentWaveIndex;
    private boolean waveInProgress;

    private int fireCooldownTicks;
    private int freezeCooldownTicks;
    private int fireAnimTicks;
    private int freezeAnimTicks;
    private int victoryDelayTicks=-1;

    private int currentLevel;
    private int maxUnlockedLevel;

    private final Score score;

    /** Creates a GameModelImpl with no preset map path. */
    public GameModelImpl(){
        this(null);
    }

    /**
     * Creates a GameModelImpl.
     * 
     * @param mapPath optional filesystem path to a custtom map JSON (may be null)
     */

    public GameModelImpl(final String mapPath){
        this.player = new PlayerImpl();
        this.wave = new WaveImpl();
        this.score = new ScoreImpl();
        this.state = GameState.MENU;
        this.activeEnemies = new ArrayList<>();
        this.spawnQueue = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.maxUnlockedLevel = SaveManager.loadMaxLevel();
        loadLevel(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadLevel(final int levelNum) {
        this.currentLevel = levelNum;
        final MapLoader loader = new MapLoader();
        MapData data = loader.loadFromClasspath("maps/level" + levelNum + ".json");
        if (data == null) {
            data = loader.loadFromClasspath("maps/map.json");
        }

        if (data != null) {
            this.map = new GameMapImpl(
                data.getWidth(),
                data.getHeight(),
                data.getBackground(),
                data.getWaypoints(),
                data.getBuildingSpots(),
                data.getDecorations());
            } else {
                final List<double[]> wp = new ArrayList<>();
                wp.add(new double[]{0, DEFAULT_WAYPOINT_Y});
                wp.add(new double[]{DEFAULT_MAP_WIDTH, DEFAULT_WAYPOINT_Y});
                this.map = new GameMapImpl(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, "", wp, new ArrayList<>(), new ArrayList<>());

            }
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.state = GameState.PLAYING;
        this.currentWaveIndex = 0;
        this.activeEnemies.clear();
        this.spawnQueue.clear();
        this.projectiles.clear();
        this.waveInProgress = false;
        this.player.reset();
        this.score.reset();
        for (final BuildingSpot spot : map.getBuildingSpots()) {
            spot.setTower(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNextWave() {
        if (!waveInProgress && currentWaveIndex < wave.getTotalWaves()) {
            currentWaveIndex++;
            spawnQueue.addAll(wave.generateWave(currentWaveIndex));
            waveInProgress = true;
            spawnCooldownTicks = INITIAL_SPAWN_COOLDOWN;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        //Victory/defeat countdown runs even when not PLAYING
        if (victoryDelayTicks > 0) {
            victoryDelayTicks--;
            return;
        }
        if (state != GameState.PLAYING) {
            return;
        }

        if (fireCooldownTicks > 0) {
            fireCooldownTicks--; 
        }
        if (freezeCooldownTicks > 0) {
            freezeCooldownTicks--;
        }
        if (fireAnimTicks > 0) {
            fireAnimTicks--;
        }
        if (freezeAnimTicks > 0) {
            freezeAnimTicks--;
        }

        // Gradual spawn: one enemy per second
        if (spawnCooldownTicks > 0) {
            spawnCooldownTicks--;
        }
        else if (!spawnQueue.isEmpty()) {
            final Enemy newEnemy = spawnQueue.remove(0);
            if (!map.getWaypoints().isEmpty()) {
                final double[] start = map.getWaypoints().get(0);
                newEnemy.setPosition(start[0], start[1]);
            }
            activeEnemies.add(newEnemy);
            spawnCooldownTicks = SPAWN_DELAY_TICKS;
        }

        // Move Enemies
        for (final Enemy enemy : activeEnemies) {
            if (!enemy.isAlive()) { 
                continue; 
            }
            enemy.tickVisuals();
            enemy.updateStatus();
            final boolean reachedEnd = enemy.moveAlongPath(map.getWaypoints());
            if (reachedEnd) {
                player.takeBaseDamage(BASE_DAMAGE_PER_ENEMY);
                enemy.setReachedEnd(true);
                enemy.takeDamage(enemy.getHealth() + FATAL_DAMAGE);
            }
        }

        // Update projectiles
        projectiles.removeIf(p -> {
            p.update();
            return !p.isAlive();
        });

        // Fire towers
        for (final Tower tower : map.getTowers()) {
            tower.tick();
            Projectile proj = null;
            for (final Enemy enemy : activeEnemies) {
                if (tower.isEnemyInRange(enemy)) {
                    proj = tower.attack(enemy);
                    if (proj != null) {
                        break;
                    }
                }
            }
        if (proj != null) {
            projectiles.add(proj);
            }
        }

        // Award rewards for killed enemis
        for (final Enemy enemy : activeEnemies) {
            if (!enemy.isAlive() && !enemy.isReachedEnd() && !enemy.isCoinAwarded()) {
                enemy.setCoinAwarded(true);
                player.addCoins(enemy.getReward());
                score.addPoints(enemy.getReward() * 10);
                SoundManager.playEnemyKilled();
            }
        }

        // Remove dead enemies
        activeEnemies.removeIf(e -> !e.isAlive());

        // Check lose condition
        if (player.getBaseHealth() <= 0) {
            state = GameState.DEFEAT;
            SoundManager.playDefeat();
            return;
        }

        // Check victory condition
        if (waveInProgress && spawnQueue.isEmpty() && activeEnemies.isEmpty()) {
            waveInProgress = false;
            if (currentWaveIndex >= wave.getTotalWaves()) {
                state = GameState.VICTORY;
                SoundManager.playVictory();
                score.addPoints(player.getBaseHealth() * 10);
                score.addPoints(player.getCoins());
                final int levelScore = score.getTotal();
                SaveManager.save(currentLevel + 1, levelScore);
                this.maxUnlockedLevel = SaveManager.loadMaxLevel();
                victoryDelayTicks = VICTORY_DELAY_TICKS;
            }
        }
    }

    /**
     * Returns the count of active (alive) enemis.
     * 
     * @return count of active enemies
     */
    public int getAliveEnemyCount() {
        int count = 0;
        for (final Enemy e : activeEnemies) {
            if (e.isAlive()) {
                count++;
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buildTowerOnSpot(final Tower tower, final BuildingSpot spot) {
        if (player.getCoins() >= tower.getType().getCost() && !spot.isOccupied() && map.addTowerToSpot(tower, spot)) {
            player.addCoins(-tower.getType().getCost());
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean upgradeTower(final Tower tower) {
        final int cost = tower.getType().getCost() / 2;
        if (player.getCoins() >= cost && tower.getLevel() < 3) {
            player.addCoins(-cost);
            tower.upgrade();
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sellTower(final BuildingSpot spot) {
        if (spot == null || !spot.isOccupied()) {
            return false;
        }

        final Tower tower = spot.getTower();
        final int refund = tower.getType().getCost() / 2;
        player.addCoins(refund);
        map.removeTowerFromSpot(spot);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void castRainOfFire() {
        if (fireCooldownTicks > 0 || state != GameState.PLAYING) {
            return;
        }

        for (final Enemy e : activeEnemies) {
            e.takeDamage(FIRE_RAIN_DAMAGE);
        }

        fireCooldownTicks = FIRE_COOLDOWN;
        fireAnimTicks = ANIMATION_DURATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void castGlobalFreeze() {
        if (freezeCooldownTicks > 0 || state != GameState.PLAYING) { 
            return;
        }
        for (final Enemy e : activeEnemies) {
            e.applySlow(FREEZE_SLOW_MULTIPLIER, FREEZE_DURATION);
        }
        freezeCooldownTicks = FREEZE_COOLDOWN;
        freezeAnimTicks = ANIMATION_DURATION;
    }

    @Override
    public List<Projectile> getProjectiles() { 
        return new ArrayList<>(projectiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Enemy> getActiveEnemies() {
        return new ArrayList<>(activeEnemies);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentWave() {
        return currentWaveIndex;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public int getTotalWaves() {
        return wave.getTotalWaves();
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public boolean isWaveInProgress() {
        return waveInProgress;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public int getFireCooldown() {
        return fireCooldownTicks;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public int getFreezeCooldown() {
        return freezeCooldownTicks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFireAnimTicks() {
        return fireAnimTicks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFreezeAnimTicks() {
        return freezeAnimTicks;
    }

    /**
     * {@inheritDoc}
     */
    @Override public GameState getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setState(final GameState s) {
        this.state=s;
    }

    /**
     * {@inheritDoc}
     */
    @Override public GameMap getMap() {
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void pause(){ 
        if (state == GameState.PLAYING) {
            state = GameState.PAUSED;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public void resume() {
        if (state == GameState.PAUSED) {
            state = GameState.PLAYING;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public Score getScore() {
        return score;
    }

     /**
      * {@inheritDoc}
      */
    @Override public Player getPlayer() {
        return player;
    }

    /** 
     * {@inheritDoc} 
     */
    @Override public boolean isVictoryRedirectReady() {
        return state == GameState.VICTORY && victoryDelayTicks == 0;
    }

    /** 
     * {@inheritDoc} 
     */
    @Override public int getCurrentLevel() {
        return currentLevel;
    }

    /** 
     * {@inheritDoc}
     */
    @Override public int getMaxUnlockedLevel() {
        return maxUnlockedLevel;
    }

}
