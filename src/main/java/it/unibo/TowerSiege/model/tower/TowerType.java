package it.unibo.TowerSiege.model.tower;

public enum TowerType {
    BASIC(50,5, 3, 60,"Torre Cristallo"),
    SNIPER(120, 15, 5, 120,"Cavaliere"),
    RAPID(80,2,3,20,"Mago"),
    ICE(90,3,3,60,"Torre Ghisccio");

    private final int cost;
    private final int damage;
    private final int range;
    private final int cooldown;
    private final String description;

    TowerType(final int  cost, final int damage, final int range, final int cooldown, final String description){
        this.cost=cost;
        this.damage=damage;
        this.range=range;
        this.cooldown=cooldown;
        this.description=description;
    }

    /**
     * Return the cost of the tower
     * @return
     */
    public int getCost(){
        return cost;
    }

    /**
     * Return the damage per attack
     * @return
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Return the attack range
     * @return
     */
    public int getRange(){
        return range;
    }

    /**
     * Return the cooldown in ticks
     * @return
     */
    public int getCooldown(){
        return cooldown;
    }

    /**
     * Return the description of the tower
     * @return
     */
    public String getDescription(){
        return description;
    }
}
