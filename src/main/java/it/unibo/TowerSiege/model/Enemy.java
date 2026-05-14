package it.unibo.TowerSiege.model;
import java.util.List;

public class Enemy {
    
    private final EnemyType type;
    private int maxHealth;
    private int health;
    private double baseSpeed;
    private int reward;

    private double pixelX;
    private double pixelY;
    private int currentWaypointIndex;

    private boolean alive;
    private int slowDurationTicks;
    private double slowMultipleyer;
    private int hitFlashTicks;
    private boolean reachedEnd = false;
    private boolean coinAwarded = false;

    public Enemy(EnemyType type, int waveNumber) {
        this.type=type;
        this.maxHealth=type.getHealth() + (waveNumber *8);
        this.baseSpeed=type.getSpeed() * 1.0;
        this.reward=type.getReward();
        this.alive=true;
        this.slowDurationTicks=0;
        this.slowMultipleyer= 1.0;
        this.hitFlashTicks=0;
        this.currentWaypointIndex=1;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        this.hitFlashTicks = 8;
        if(this.health <= 0) {
            this.health=0;
            this.alive = false;
        }
    }

    public void setReachedEnd(boolean val) {
        this.reachedEnd = val;
    }

    public boolean isReachedEnd() {
        return reachedEnd;
    }

    public void setCoinAwarded(boolean val) {
        this.coinAwarded=val;
    }

    public boolean isCoinAwarded() {
        return coinAwarded;
    }

    public void tickVisuals() {
        if( hitFlashTicks > 0) {
            hitFlashTicks--;
        }
    }

    public int getHitFlashTicks() {
        return hitFlashTicks;
    }

    public void updateStatus() {
        if(slowDurationTicks > 0) {
            slowDurationTicks--;
            if (slowDurationTicks == 0) slowMultipleyer = 1.0;
        }
    }

    public double getEffectiveSpeed() {
        return baseSpeed * slowMultipleyer;
    }

    

    public boolean moveAlongPath(List<double[]> waypoints) {
        if(waypoints == null || waypoints.isEmpty() || currentWaypointIndex >= waypoints.size()) {
            return true;
        }

        double[] target = waypoints.get(currentWaypointIndex);
        double dx = target[0] - pixelX;
        double dy = target[1] - pixelY;
        double dist = Math.sqrt(dx*dx + dy*dy);
        double step = getEffectiveSpeed();

        if(dist <= step) {
            pixelX = target[0];
            pixelY = target[1];
            currentWaypointIndex++;
            return currentWaypointIndex >= waypoints.size();
        } else {
            pixelX += (dx/dist) * step;
            pixelY += (dy/dist) * step;
        }
        return false;
    }
        public EnemyType getType() {
            return type;
        }

        public int getMaxHealth() {
            return maxHealth;
        }

        public int getHealth() {
            return health;
        }

        public int getReward() {
            return reward;
        }

        public boolean isAlive() {
            return alive;
        }

        public double getPixelX() {
            return pixelX;
        }

        public double getPiexelY() {
            return pixelY;
        }

        public void setPosition(double x,double y) {
            this.pixelX = x;
            this.pixelY = y;
        }
}
