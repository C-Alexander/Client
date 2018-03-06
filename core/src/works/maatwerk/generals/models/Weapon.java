package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Weapon {
    private String name;
    private Stats stats;
    private boolean canHeal;
    private int rng; //attack range
    
    public Weapon(String name, int rng, Stats stats, boolean canHeal) {
        this.name = name;
        this.rng = rng;
        this.stats = stats;
        this.canHeal = canHeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public boolean isCanHeal() {
        return canHeal;
    }

    public void setCanHeal(boolean canHeal) {
        this.canHeal = canHeal;
    }

    public int getRng() {
        return rng;
    }

    public void setRng(int rng) {
        this.rng = rng;
    }

    @Override
    public String toString() {
        return "Weapon{" + "name=" + name + ", stats=" + stats + ", canHeal=" + canHeal + ", rng=" + rng + '}';
    }
}
