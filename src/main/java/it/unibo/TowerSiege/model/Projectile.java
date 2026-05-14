package it.unibo.TowerSiege.model;

public class Projectile {
    private final Tower source;
    private final Enemy target;
    private double x;
    private double y;
    private final double speed=15.0;
    private boolean alive=true;

    public Projectile(Tower source, Enemy target){
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
        double dist=Math.sqrt(dx*dx + dy*dy);

        
    }
}
