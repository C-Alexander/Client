package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Race {
    private final String name;
    private final Stats stats;
    
    /**
     * 
     * @param name
     * @param stats 
     */
    public Race(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
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
    @Override
    public String toString() {
        return "Race{" + "name=" + name + ", stats=" + stats + '}';
    }
}