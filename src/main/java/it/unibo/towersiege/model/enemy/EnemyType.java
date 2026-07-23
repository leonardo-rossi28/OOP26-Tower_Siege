package it.unibo.towersiege.model.enemy;

/**
 * Represents the different types of enemies in the game.
 */
public enum EnemyType {
    BASIC(80, 1, 10, "Basic Ogre"),
    FAST(45, 2, 15, "Fast Goblin"),
    TANK(200, 1, 25, "Huge Ogre");

    private final int health;
    private final int speed;
    private final int reward;
    private final String description;

    EnemyType(final int health, final int speed, final int reward, final String description) {
        this.health = health;
        this.speed = speed;
        this.reward = reward;
        this.description = description;
    }

    /**
     * Gets the base healt.
     * 
     * @return healt
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the movement speed.
     * 
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the gold reward.
     * 
     * @return reward
     */
    public int getReward() {
        return reward;
    }

    /**
     * Gets the enemy description.
     * 
     * @return description
     */
    public String stringDescription() {
        return description;
    }
}
