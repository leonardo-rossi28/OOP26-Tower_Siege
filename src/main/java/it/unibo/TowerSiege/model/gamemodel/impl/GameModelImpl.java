package it.unibo.TowerSiege.model.gamemodel.impl;

import it.unibo.TowerSiege.commons.mapdata.MapData;
import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
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

import java.util.ArrayList;
import java.util.List;

public class GameModelImpl implements GameModel {

    private GameState state;
    private GameMap map;
    private final Player player;
    private final Wave wave;
    private final List<Enemy> activeEnemies;
    private final List<Enemy> spawnQueue;
    private final List<Projectile> projectile;
    private int  spawnCooldownTicks;
    private int currentWaveIndex;
    private boolean waveInProgress;
    private int fireCooldownTicks;
    private int freezeCooldownTicks;
    private static final int FIRE_COOLDOWN = 900; //15 seconds
    private static final int FREEZE_COOLDOWN = 600; //10 seconds
    private int fireAnimTicks;
    private int freezeAnimTicks;
    private int victoryDelayTicks=-1;
    private static final int SPAWN_DELAY_TICKS = 60; //1 second at 60fps
    private static final int BASE_DAMAGE_PER_ENEMY = 10;

    private int currentLevel;
    private int maxUnlockedLevel;
    private final Score score;










    /** Stefano's code */





    /** Creates a GameModelImpl with no preset map path. */
    public GameModelImpl(){
        this(null);
    }




    /**
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


    /** Stefano's code */








    /**
     * {@inheritDoc}
     */
    @Override
    public void update(){
        //Update projectiles
        projectile.removeIf(p -> { p.update(); return !p.isAlive();});

        //Towers attack
        for (final Tower tower : map.getTowers()) {
            tower.tick();
            if(!tower.isAlive()) { continue; }
            for (final Enemy enemy : activeEnemies) {
                if(enemy.isAlive()) {
                    final Projectile p = tower.attack(enemy);
                    if(p != null) {
                        projectile.add(p);
                    }
                }
            }
        }

        //Win / lose check
        if(!player.isBaseAlive()){
            state=GameState.DEFEAT;
        } else if (waveInProgress && spawnQueue.isEmpty() && getAliveEnemyCount()==0) {
            waveInProgress=false;
            if(currentWaveIndex >= wave.getTotalWaves()){
                state= GameState.VICTORY;
                victoryDelayTicks=180; 
                if(currentLevel < 3 && currentLevel >= maxUnlockedLevel){
                    maxUnlockedLevel = currentLevel + 1;
                }
            }
        }

        //collect rewards and remove dead enemies
        activeEnemies.removeIf(e->{
            if(!e.isAlive()){
                if(!e.isReachedEnd() && !e.isCoinAwarded()){
                    player.addCoins(e.getReward());
                    score.addPoints(e.getReward()*10);
                    e.setCoinAwarded(true);
                }
                return true;
            }
            return false;
        });

        //gradual spawn of the enemy
        if(spawnCooldownTicks > 0) {
            spawnCooldownTicks--;
        } else if (!spawnQueue.isEmpty()) {
            final Enemy newEnemy = spawnQueue.remove(0);
            if(!map.getWaypoints().isEmpty()) {
                final double[] start = map.getWaypoints().get(0);
                newEnemy.setPosition(start[0], start[1]);
            }
            activeEnemies.add(newEnemy);
            spawnCooldownTicks = SPAWN_DELAY_TICKS;
        }

        //move the enemies
        for (final Enemy enemy : activeEnemies) {
            if (!enemy.isAlive()) { continue; }
            enemy.tickVisuals();
            enemy.updateStatus();
            final boolean reachedEnd = enemy.moveAlongPath(map.getWaypoints());
            if (reachedEnd) {
                player.takeBaseDamage(BASE_DAMAGE_PER_ENEMY);
                enemy.setReachedEnd(true);
                enemy.takeDamage(enemy.getHealth()+999);
            }
        }
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
    public void startNextWave() {
        if(!waveInProgress && currentWaveIndex < wave.getTotalWaves()) {
            currentWaveIndex++;
            spawnQueue.addAll(wave.generateWave(currentWaveIndex));
            waveInProgress = true;
            spawnCooldownTicks = 30;
        }
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
        for  (final Enemy e : activeEnemies) {
            e.applySlow(0.3, 180);
        }
        freezeCooldownTicks = FREEZE_COOLDOWN;
        freezeAnimTicks = 60;
    }


    @Override
    public List<Projectile> getProjectiles() { return new ArrayList<>(projectile); }

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


}
