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
public class Growth {
    //This class determines the growth / leveling system. 
    //This is currently a stub class with a general idea, implement as new rules are set
    private static final int maxLevel = 42;
    private int totalXp;
    private int[] levelXpRequirement = new int[maxLevel];
    private int level;
    private int stats; //total stats gained from growth.
    private String[] ranks = new String[maxLevel];
    private String currentRank;
    
    /***
     * Get value of stats earned through character growth.
     * Proof of concept: 
     * Stats are based on bonusStats assigned to currently earned level;
     * 
     * @return stats
     * 
     */
    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }
    
    /**
     * Get the value of level depending on totalXP earned.
     * proof of concept: array [level,xpRequirement] --> level 10 requires 10.000 xp = [9, 10000]
     * so level = totalXP less than y's X value + 1 ... sort of?
     * 
     * @return the value of level
     */    
    public int getLevel(){
        //implement
        return level;
    }  

    public static int getMaxLevel() {
        return maxLevel;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public Growth(int totalXp, int level) {
        this.totalXp = totalXp;
        this.level = level;
    }
    
    /**
     * Add xp to totalXP
     *
     * @return the value of totalXP after calculation
     */    
    public int gainXP(int xp){
        totalXp += xp;
        return totalXp;
    }      
    
    /**
     * level increases and therefore growth increases based on level
     * 
     */
    public void levelUp(){
        //implement
    }
    
    /**
     * Rank changes and therefore growth changes based on rank
     * 
     */
    public void Promote(){
        //implement
    }
}
