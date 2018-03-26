package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Stats {
    private int healthPoints;
    private int attack;
    private int defence;
    private int movement;
    private int speed;
    
    /**
     * Default constructor. All attributes are 0.
     */
    public Stats() {
        healthPoints = 2;
        attack = 1;
        defence = 0;
        movement = 1;
        speed = 1;
    }
    
    /**
     * 
     * @param healthpoints
     * @param attack
     * @param defence
     * @param movement
     * @param speed 
     */
    public Stats(int healthpoints, int attack, int defence, int movement, int speed) {
        this.healthPoints = healthpoints;
        this.attack = attack;
        this.defence = defence;
        this.movement = movement;
        this.speed = speed;
    }
    
    /**
     * 
     * @return 
     */
    public int getHealthPoints() {
        return healthPoints;
    }
    
    /**
     * 
     * @return 
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * 
     * @return 
     */
    public int getDefence() {
        return defence;
    }
    
    /**
     * 
     * @return 
     */
    public int getMovement() {
        return movement;
    }
    
    /**
     * 
     * @return 
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * 
     * @param healthPoints 
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
    
    /**
     * 
     * @param attack 
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    /**
     * 
     * @param defence 
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }
    
    /**
     * 
     * @param movement 
     */
    public void setMovement(int movement) {
        this.movement = movement;
    }
    
    /**
     * 
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Adds the stats argument to this instance
     * 
     * @param stats 
     */
    public void addToThis(Stats stats) {
        if(stats == null)
            return;
        this.attack += stats.attack;
        this.defence += stats.defence;
        this.healthPoints += stats.healthPoints;
        this.movement += stats.movement;
        this.speed += stats.speed;
    }
    
    /**
     * Adds the stats argument to this instance values and creates a new Stats instance.
     * 
     * @param stats
     * @return 
     */
    public Stats addToNew(Stats stats) {
        Stats output = new Stats();
        output.addToThis(this);
        output.addToThis(stats);
        return output;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Stats{" + "hp=" + healthPoints + ", att=" + attack + ", def=" + defence + ", mvd=" + movement + ", spd=" + speed + '}';
    }
}