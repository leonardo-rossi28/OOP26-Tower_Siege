package it.unibo.TowerSiege.model.gamemodel.impl;

import it.unibo.TowerSiege.commons.mapdata.MapData;
import it.unibo.TowerSiege.commons.maploader.MapLoader;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.commons.savemanager.SaveManager;
import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.gamemap.api.GameMap;
import it.unibo.TowerSiege.model.gamemap.impl.GameMapImpl;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamestate.GameState;
import it.unibo.TowerSiege.model.player.api.Player;
import it.unibo.TowerSiege.model.player.impl.PlayerImpl;
import it.unibo.TowerSiege.model.projectile.api.Projectile;
import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.wave.api.Wave;
import it.unibo.TowerSiege.model.wave.impl.WaveImpl;
import it.unibo.TowerSiege.model.score.api.Score;
import it.unibo.TowerSiege.model.score.impl.ScoreImpl;
import it.unibo.TowerSiege.commons.soundmanager.SoundManager;

import java.util.ArrayList;
import java.util.List;

public class GameModelImpl implements GameModel {

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
    private static final int FIRE_COOLDOWN = 900; //15 seconds
    private static final int FREEZE_COOLDOWN = 480; //10 seconds
    private int fireAnimTicks;
    private int freezeAnimTicks;
    private int victoryDelayTicks=-1;
    private static final int SPAWN_DELAY_TICKS = 60; //1 second at 60fps
    private static final int BASE_DAMAGE_PER_ENEMY = 10;

    private int currentLevel;
    private int maxUnlockedLevel;
    private final Score score;

    /** Creates a GameModelImpl with no preset map path. */
    public GameModelImpl(){
        this(null);
    }

    /**
     * Creates a GameModelImpl
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
    public void loadLevel(final int levelNum){
        this.currentLevel = levelNum;
        final MapLoader loader = new MapLoader();
        MapData data = loader.loadFromClasspath("maps/level" + levelNum + ".json");
        if (data == null){
            data = loader.loadFromClasspath("maps/map.json");
        }

        if (data != null){
            this.map = new GameMapImpl(
                data.getWidth(),
                data.getHeight(),
                data.getBackground(),
                data.getWaypoints(),
                data.getBuildingSpots(),
                data.getDecorations());
            } else{
                final List<double[]> wp = new ArrayList<>();
                wp.add(new double[]{0, 300});
                wp.add(new double[]{800, 300});
                this.map = new GameMapImpl(800, 600, "", wp, new ArrayList<>(), new ArrayList<>());

            }
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(){
        this.state = GameState.PLAYING;
        this.currentWaveIndex = 0;
        this.activeEnemies.clear();
        this.spawnQueue.clear();
        this.projectiles.clear();
        this.waveInProgress = false;
        this.player.reset();
        this.score.reset();
        for (final BuildingSpot spot : map.getBuildingSpots()){
            spot.setTower(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNextWave(){
        if(!waveInProgress && currentWaveIndex < wave.getTotalWaves()){
            currentWaveIndex++;
            spawnQueue.addAll(wave.generateWave(currentWaveIndex));
            waveInProgress = true;
            spawnCooldownTicks = 30;
        }
    
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(){
        //Victory/defeat countdown runs even when not PLAYING
        if (victoryDelayTicks > 0) {
            victoryDelayTicks--;
            return;
        }
        if (state != GameState.PLAYING) {
            return;
        }

        if(fireCooldownTicks > 0) { fireCooldownTicks--; }
        if(freezeCooldownTicks > 0) { freezeCooldownTicks--; }
        if(fireAnimTicks > 0) { fireAnimTicks--; }
        if(freezeAnimTicks > 0) { freezeAnimTicks--; }

        //Gradual spawn: one enemy per second
        if(spawnCooldownTicks > 0) {
            spawnCooldownTicks--;
        } else if (!spawnQueue.isEmpty()) {
            final Enemy newEnemy = spawnQueue.remove(0);
            if (!map.getWaypoints().isEmpty()) {
                final double[] start = map.getWaypoints().get(0);
                newEnemy.setPosition(start[0], start[1]);
            }
            activeEnemies.add(newEnemy);
            spawnCooldownTicks = SPAWN_DELAY_TICKS;
        }

        //Move Enemies
        for (final Enemy enemy : activeEnemies) {
            if (!enemy.isAlive()) { continue; }
            enemy.tickVisuals();
            enemy.updateStatus();
            final boolean reachedEnd = enemy.moveAlongPath(map.getWaypoints());
            if(reachedEnd) {
                player.takeBaseDamage(BASE_DAMAGE_PER_ENEMY);
                enemy.setReachedEnd(true);
                enemy.takeDamage(enemy.getHealth() + 999);
            }
        }

        //Update projectiles
        projectiles.removeIf(p -> { p.update(); return !p.isAlive();});

        //Towers attack
        for (final Tower tower : map.getTowers()) {
            tower.tick();
            if(!tower.isAlive()) { continue; }
            for (final Enemy enemy : activeEnemies) {
                if(enemy.isAlive()) {
                    final Projectile p = tower.attack(enemy);
                    if(p != null) {
                        projectiles.add(p);
                        break;
                    }
                }
            }
        }

        //Win / lose check
        if(!player.isBaseAlive()){
            state=GameState.DEFEAT;
            SoundManager.playDefeat();
        } else if (waveInProgress && spawnQueue.isEmpty() && getAliveEnemyCount()==0) {
            waveInProgress=false;
            if(currentWaveIndex >= wave.getTotalWaves()){
                state= GameState.VICTORY;
                SoundManager.playVictory();
                victoryDelayTicks=180; 
                if(currentLevel < 3 && currentLevel >= maxUnlockedLevel){
                    maxUnlockedLevel = currentLevel + 1;
                }
                SaveManager.save(
                    maxUnlockedLevel,
                    Math.max(score.getTotal(), SaveManager.loadBestScore())
                );
            }
        }

        //Collect rewards and remove dead enemies
        activeEnemies.removeIf(e->{
            if(!e.isAlive()){
                if(!e.isReachedEnd() && !e.isCoinAwarded()){
                    player.addCoins(e.getReward());
                    score.addPoints(e.getReward() * 10);
                    e.setCoinAwarded(true);
                    SoundManager.playEnemyKilled();
                }
                return true;
            }
            return false;
        });
    }

       /**
     * Counts the number of enemies alive.
     * 
     * @return count alive enemy
     */
    private int getAliveEnemyCount() {
        int count = 0;
        for (final Enemy e : activeEnemies) {
            if(e.isAlive()) {
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
        if (player.getCoins() >= tower.getType().getCost() && !spot.isOccupied()) {
            if(map.addTowerToSpot(tower, spot)) {
                player.addCoins(-tower.getType().getCost());
                return true;
            }
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
        if (fireCooldownTicks > 0 || state != GameState.PLAYING) {return; }
        for (final Enemy e : activeEnemies) {
            e.takeDamage(50);
        }
        fireCooldownTicks = FIRE_COOLDOWN;
        fireAnimTicks = 60;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void castGlobalFreeze() {
        if (freezeCooldownTicks > 0 || state != GameState.PLAYING) { return;}
        for (final Enemy e : activeEnemies) {
            e.applySlow(0.3, 180);
        }
        freezeCooldownTicks = FREEZE_COOLDOWN;
        freezeAnimTicks = 60;
    }


    @Override
    public List<Projectile> getProjectiles() { return new ArrayList<>(projectiles); }

    /**{@inheritDoc} */
    @Override
    public List<Enemy> getActiveEnemies() { return new ArrayList<>(activeEnemies);}

    /**{@inheritDoc} */
    @Override
    public int getCurrentWave() { return currentWaveIndex; }

    /**{@inheritDoc} */
    @Override
    public int getTotalWaves() { return wave.getTotalWaves(); }

    /**{@inheritDoc} */
    @Override
    public boolean isWaveInProgress() { return waveInProgress; }

    /**{@inheritDoc} */
    @Override
    public int getFireCooldown() { return fireCooldownTicks; }

    /**{@inheritDoc} */
    @Override
    public int getFreezeCooldown() { return freezeCooldownTicks; }

    /**{@inheritDoc} */
    @Override
    public int getFireAnimTicks() { return fireAnimTicks; }

    /**{@inheritDoc} */
    @Override
    public int getFreezeAnimTicks() { return freezeAnimTicks; }

    /**{@inheritDoc} */
    @Override public GameState getState(){ return state;}

    /**{@inheritDoc} */
    @Override public void setState(final GameState s ){ this.state=s;}

    /** {@inheritDoc} */
    @Override public GameMap getMap()   { return map; }

    /**{@inheritDoc} */
    @Override public void pause(){ 
        if(state==GameState.PLAYING){
            state=GameState.PAUSED;
        }
    }

    /**{@inheritDoc} */
    @Override public void resume(){
        if(state==GameState.PAUSED){
            state=GameState.PLAYING;
        }
    }

     /**{@inheritDoc} */
    @Override public Score getScore()   { return score; }

     /**{@inheritDoc} */
    @Override public Player getPlayer() { return player; }

    /** {@inheritDoc} */
    @Override public boolean isVictoryRedirectReady() {
        return state == GameState.VICTORY && victoryDelayTicks == 0;
    }

    /** {@inheritDoc} */
    @Override public int getCurrentLevel()  { return currentLevel; }

    /** {@inheritDoc} */
    @Override public int getMaxUnlockedLevel()  { return maxUnlockedLevel; }

}
