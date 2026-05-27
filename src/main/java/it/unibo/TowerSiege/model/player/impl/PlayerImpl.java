package it.unibo.towerdefense.model.player.impl;

import it.unibo.towerdefense.commons.gameconstants.GameConstants;
import it.unibo.towerdefense.model.player.api.Player;

/**
 * it represents the player state: coins and base health
 */

public class PlayerImpl implements Player {

    private int coins;
    private int baseHealth;

    /** a new PlayerImpl and resets to starting values **/
    public PlayerImpl(){
        reset();
    }


    /**
     * {@inheritDoc}
     * 
     **/
    @Override
    public void addCoins(final int amount){
        this.coins += amount;
    }


    @Override
    public boolean spendCoins( final int amount ){
        if (this.coins >= amount){
            this.coins -= amount;
            return true;

        }

        return false;
    }


    @Override
    public int getCoins(){
        return coins;
    }

    @Override
    public boolean isBaseAlive(){

        return baseHealth > 0;
    }

}