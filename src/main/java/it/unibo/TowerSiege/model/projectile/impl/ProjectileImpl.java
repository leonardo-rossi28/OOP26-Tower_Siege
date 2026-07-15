package it.unibo.TowerSiege.model.projectile.impl;

import it.unibo.TowerSiege.model.enemy.api.Enemy;
import it.unibo.TowerSiege.model.tower.TowerType;
import it.unibo.TowerSiege.model.tower.api.Tower;
import it.unibo.TowerSiege.model.projectile.api.Projectile;

/**
 * This class represent a projectile that moves towards the target
 * The projectile hit if the enemy is in the same pixel otherwise the 
 * projectile continues to move.
 */
public class ProjectileImpl implements Projectile{
   
    private final Tower source;
    private final Enemy target;
    private double x;
    private double y;
    private static final double SPEED = 15.0;
    private boolean alive=true;



    /**
     * 
     * @param source
     * @param target
     */
    public ProjectileImpl(final Tower source, final Enemy target){
        this.source=source;
        this.target=target;
        this.x=source.getPixelX();
        this.y=source.getPixelY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(){
        if(!target.isAlive()){
            this.alive=false;
            return;
        }

        final double tx=target.getPixelX()+20;
        final double ty=target.getPixelY()+20;
        final double dx=tx-x;
        final double dy=ty-y;
        final double dist = Math.sqrt(dx*dx + dy*dy);

        if( dist <= SPEED){
            target.takeDamage(source.getDamage());
            if(source.getType()==TowerType.ICE){
                target.applySlow(0.5,20);
            }
            this.alive=false;
        }else{
            this.x +=(dx/dist)*SPEED;
            this.y +=(dy/dist)*SPEED;
        }
        
        
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double getX(){
        return x;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getY(){
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TowerType getSourceTowerType(){
        return source.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive(){
        return alive;
    }

}
