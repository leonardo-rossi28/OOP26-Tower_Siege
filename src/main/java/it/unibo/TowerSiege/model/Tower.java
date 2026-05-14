package it.unibo.TowerSiege.model;



public class Tower {
    
    private final TowerType type;
    private double pixelX;
    private double pixelY;
    private int level;
    private boolean alive;
    private int cooldownTicks; 

    public Tower(TowerType type) {  
        this.type=type;
        this.level=1;  
        this.alive=true;
        this.cooldownTicks=0; 
    }

    public void Position(double x, double y){
        this.pixelX=x;
        this.pixelY=y;
    }

    public void tick(){  
        if(cooldownTicks > 0){
            cooldownTicks--;
        }
    }

    public boolean isEnemyInRange(Enemy enemy){
        double dx= enemy.getPixelX()-pixelX;
        double dy= enemy.getPiexelY() - pixelY;
        double distSq= dx*dx + dy*dy;
        double rangePx= type.getRange() * 40;
        return distSq <= (rangePx * rangePx);
    }

    public void upgrade(){
        this.level++;
    }

    public TowerType getType(){
        return type;
    }
    public double getPixelX(){
        return pixelX;
    }

    public double getPixelY(){
        return pixelY;
    }

    public int getLevel(){
        return level;
    }

    public boolean isAlive(){
        return alive;
    }

    public int getRange(){
        return type.getRange();
    }
     public int getDamage(){
        return type.getDamage() + (level -1)*2;
     }
    




}
