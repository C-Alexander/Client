/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Weapon {
    private String name;
    private Stats stats;
    private boolean canHeal;

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

    public Weapon(String name, Stats stats, boolean canHeal) {
        this.name = name;
        this.stats = stats;
        this.canHeal = canHeal;
    }

    @Override
    public String toString() {
        return "Weapon{" + "name=" + name + ", stats=" + stats + ", canHeal=" + canHeal + '}';
    }
}
