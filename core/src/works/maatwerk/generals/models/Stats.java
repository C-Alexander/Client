package works.maatwerk.generals.models;

import java.text.MessageFormat;

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
        healthPoints = 0;
        attack = 0;
        defence = 0;
        movement = 0;
        speed = 0;
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
        return MessageFormat.format("hp={0}  att={1}  def={2}  mvd={3}  spd={4}", healthPoints, attack, defence, movement, speed);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.healthPoints;
        hash = 41 * hash + this.attack;
        hash = 41 * hash + this.defence;
        hash = 41 * hash + this.movement;
        hash = 41 * hash + this.speed;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stats other = (Stats) obj;
        if (this.healthPoints != other.healthPoints) {
            return false;
        }
        if (this.attack != other.attack) {
            return false;
        }
        if (this.defence != other.defence) {
            return false;
        }
        if (this.movement != other.movement) {
            return false;
        }
        return this.speed == other.speed;
    }

    public Stats cloneStats() {
        Stats output = new Stats();
        output.attack = this.attack;
        output.defence = this.defence;
        output.healthPoints = this.healthPoints;
        output.movement = this.movement;
        output.speed = this.speed;
        return output;
    }
}