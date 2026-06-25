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

import java.util.ArrayList;
import java.util.List;

public class GameModelImpl implements GameModel {

    private GameState state;
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
    public boolean upgradeTower(final Tower tower){
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
