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
public class Race {
    
    private String name;
    private Stats stats;

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

    public Race(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "Race{" + "name=" + name + ", stats=" + stats + '}';
    }
}
