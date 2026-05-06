package main.java.it.unibo.towersiege.model;

public enum EnemyType {
    BASIC(80, 1, 10, "Basic Ogre"),
    FAST(45, 2, 15, "Fast Goblin"),
    TANK(200, 1, 25, "Huge Ogre");

    private final int health;
    private final int speed;
    private final int reward;
    private final String description;

    EnemyType(int health, int speed, int reward, String description) {
        this.health = health;
        this.speed = speed;
        this.reward = reward;
        this.description = description;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }

    public String StringDescription() {
        return description;
    }
}
