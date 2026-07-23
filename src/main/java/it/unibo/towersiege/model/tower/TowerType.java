package it.unibo.towersiege.model.tower;

/**
 * Represents the available tower types in the game with their attributes.
 */
public enum TowerType {

    BASIC(50, 5, 3, 60, "Torre Cristallo"),
    SNIPER(120, 15, 5, 120, "Cavaliere"),
    RAPID(80, 2, 3, 20, "Mago"),
    ICE(90, 3, 3, 60, "Torre Ghiaccio");

    private final int cost;
    private final int damage;
    private final int range;
    private final int cooldown;
    private final String description;

    /**
     * Constructs a new TowerType with the specified base attributes.
     * 
     * @param cost        the purchase cost of the tower
     * @param damage      the base damage per attack
     * @param range       the range attack of the towers
     * @param cooldown    the cooldown beetwen attacks in ticks
     * @param description description the display description of the tower
     */
    TowerType(final int cost, final int damage, final int range, final int cooldown, final String description) {
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.description = description;
    }

    /**
     * Returns the cost of the tower.
     * 
     * @return the cost of the tower
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns the damage per attack.
     * 
     * @return the damage for attack
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the attack range.
     * 
     * @return the attack range
     */
    public int getRange() {
        return range;
    }

    /**
     * Returns the cooldown in ticks.
     * 
     * @return the cooldown in ticks
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Returns the description of the tower.
     * 
     * @return the description of the tower
     */
    public String getDescription() {
        return description;
    }
}
