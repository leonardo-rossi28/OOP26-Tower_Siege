package it.unibo.towersieges.model.tower.impl;

import it.unibo.towersieges.model.enemy.api.Enemy;
import it.unibo.towersieges.model.projectile.api.Projectile;
import it.unibo.towersieges.model.projectile.impl.ProjectileImpl;
import it.unibo.towersieges.model.tower.TowerType;
import it.unibo.towersieges.model.tower.api.Tower;




public class TowerImpl implements Tower{
    
    private final TowerType type;
    private double pixelX;
    private double pixelY;
    private int level;
    private final boolean alive;
    private int cooldownTicks; 


    /**
     * @param type
    */
    public TowerImpl(TowerType type) {  
        this.type=type;
        this.level=1;  
        this.alive=true;
        this.cooldownTicks=0; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(double x, double y){
        this.pixelX=x;
        this.pixelY=y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick(){  
        if(cooldownTicks > 0){
            cooldownTicks--;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnemyInRange(Enemy enemy){
        double dx= enemy.getPixelX()-pixelX;
        double dy= enemy.getPixelY() - pixelY;
        double distSq= dx*dx + dy*dy;
        double rangePx= type.getRange() * 40;
        return distSq <= (rangePx * rangePx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upgrade(){
        this.level++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TowerType getType(){
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPixelX(){
        return pixelX;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getPixelY(){
        return pixelY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel(){
        return level;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive(){
        return alive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRange(){
        return type.getRange();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
     public int getDamage(){
        return type.getDamage() + (level -1) * 2;
     }
    
    /**
      * {@inheritDoc}
    */
     @Override
     public Projectile attack(final Enemy enemy){
        if(!alive || cooldownTicks > 0){
            return null;
        }
        if(isEnemyInRange(enemy)){
            cooldownTicks=type.getCooldown();
            return new ProjectileImpl(this,enemy);
        }
        return null;
     }




}
