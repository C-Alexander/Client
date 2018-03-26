package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Weapon {
    private final String name;
    private final Stats stats;
    private final Debuff statusEffect;
    private final boolean canHeal;
    private final int range; //attack range
    
    /**
     * 
     * @param name
     * @param range
     * @param stats
     * @param canHeal
     * @param statuseffect 
     */
    public Weapon(String name, int range, Stats stats, boolean canHeal, Debuff statuseffect) {
        this.name = name;
        this.range = range;
        this.stats = stats;
        this.canHeal = canHeal;
        this.statusEffect = statuseffect;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return 
     */
    public Stats getStats() {
        return stats;
    }
    
    /**
     * 
     * @return 
     */
    public Debuff getStatusEffect() {
        return statusEffect;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isCanHeal() {
        return canHeal;
    }
    
    /**
     * 
     * @return 
     */
    public int getRange() {
        return range;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Weapon{" + "name=" + name + ", stats=" + stats + ", canHeal=" + canHeal + ", range=" + range + '}';
    }
}