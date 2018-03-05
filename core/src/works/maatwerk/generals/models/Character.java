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
public class Character {
    private Stats baseStats; //basisstats
    private Stats gameStats; //echte/current stats na stat manipulatie (buffs / debuffs / ras / wapen / damage / heals etc.)
    private Race race;
    private Weapon wpn;
    private Debuff debuff;
    private boolean alive;

    public Stats getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(Stats baseStats) {
        this.baseStats = baseStats;
    }

    public Stats getGameStats() {
        return gameStats;
    }

    public void setGameStats(Stats gameStats) {
        this.gameStats = gameStats;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Weapon getWpn() {
        return wpn;
    }

    public void setWpn(Weapon wpn) {
        this.wpn = wpn;
    }

    public Debuff getDebuff() {
        return debuff;
    }

    public void setDebuff(Debuff debuff) {
        this.debuff = debuff;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Character(Stats baseStats, Stats gameStats, Race race, Weapon wpn, Debuff debuff, boolean alive) {
        this.baseStats = baseStats;
        this.gameStats = gameStats;
        this.race = race;
        this.wpn = wpn;
        this.debuff = debuff;
        this.alive = alive;
    }


    /**
     * updates the class's properties
     * 
     */
    public void update() {
        //implement
    }
    
    /**
     * Manipulates stats of this character and enemy character according to 
     * battle calculations
     *
     * @return the value of enemy character
     */
    public Character attack(Character enemy) {
        //implement
        return null;
    }
    
    /**
     * Manipulates gameStats of this character according to healing calculations
     *
     */
    public void heal(Character ally) {
        //implement
    }
    
    /**
     * manipulates gameStats temporarily according to tile effect
     *
     */
    public void bonus(Tile bonusTile) {
        //implement
    }
}
