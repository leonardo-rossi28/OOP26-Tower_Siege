package it.unibo.TowerSiege.model.gamemodel.impl;

import it.unibo.TowerSiege.model.buildingspot.api.BuildingSpot;
import it.unibo.TowerSiege.model.buildingspot.impl.BuildingSpotImpl;
import it.unibo.TowerSiege.model.gamemodel.api.GameModel;
import it.unibo.TowerSiege.model.gamemodel.impl.GameModelImpl;
import it.unibo.TowerSiege.model.projectile.api.Projectile;
import it.unibo.TowerSiege.model.projectile.impl.ProjectileImpl;
import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.tower.impl.TowerImpl;
import it.unibo.TowerSiege.model.player.api.Player;
import it.unibo.TowerSiege.model.player.impl.PlayerImpl;
import it

import java.util.ArrayList;
import java.util.List;

public class GameModelImpl implements GameModel {
    private final List<Projectile> projectile;

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
    }
}
