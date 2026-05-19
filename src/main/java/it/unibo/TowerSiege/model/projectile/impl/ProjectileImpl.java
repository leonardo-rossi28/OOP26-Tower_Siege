package it.unibo.TowerSiege.model.projectile.impl;

import it.unibo.TowerSiege.model.enemy.impl.Enemy;
import it.unibo.TowerSiege.model.tower.TowerType;
import it.unibo.TowerSiege.model.tower.impl.TowerImpl;

/**
 * This class represent a projectile that moves towards the target
 * The projectile hit if the enemy is in the same pixel otherwise the 
 * projectile continues to move.
 */
public class Projectile {
    private final TowerImpl source;
    private final Enemy target;
    private double x;
    private double y;
    private final double speed=15.0;
    private boolean alive=true;


    public Projectile(TowerImpl source, Enemy target){
        this.source=source;
        this.target=target;
        this.x=source.getPixelX();
        this.y=source.getPixelY();
    }

    public void update(){
        if(!target.isAlive()){
            this.alive=false;
            return;
        }

        double tx=target.getPixelX()+20;
        double ty=target.getPiexelY()+20;

        double dx=tx-x;
        double dy=ty-y;
        double dist = Math.sqrt(dx*dx + dy*dy);

        if( dist <= speed){
            target.takeDamage(source.getDamage());
            if(source.getType()==TowerType.ICE){
                target.applySlow(0.5,20);
            }
            this.alive=false;
        }else{
            this.x +=(dx/dist)*speed;
            this.y +=(dy/dist)*speed;
        }
        
        
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public TowerType getSourceTowerType(){
        return source.getType();
    }
    public boolean isAlive(){
        return alive;
    }

}
