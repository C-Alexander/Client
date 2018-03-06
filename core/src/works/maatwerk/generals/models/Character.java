package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Character {
    private Stats baseStats;    //basisstats
    /**
     * real /current stats after stat manipulation (buffs / debuffs / race / 
     * weapon / damage / heals etc.) 
     * 
     * >> allways keep gamestats up-to-date!!! <<
     */
    private Stats gameStats;   
    private Race race;
    private Weapon wpn;
    private Debuff debuff;
    private boolean alive;
    private String rank;
    private int xp; //cannot exceed xp needed for next level (100*1.2^lvl)
    private int lvl;
    
    public Character(Stats baseStats, Stats gameStats, Race race, Weapon wpn, Debuff debuff, boolean alive) {
        this.baseStats = baseStats;
        this.gameStats = gameStats;
        this.race = race;
        this.wpn = wpn;
        this.debuff = debuff;
        this.alive = alive;
    }

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
    
    /**
     * Get the value of rank
     *
     * @return the value of rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Set the value of rank
     *
     * @param rank new value of rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }
    
    /**
     * Get the value of xp
     *
     * @return the value of xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * Set the value of xp
     *
     * @param xp new value of xp
     */
    public void setXp(int xp) {
        this.xp = xp;
    }
    
    /**
     * Get the value of lvl
     *
     * @return the value of lvl
     */
    public int getLvl() {
        return lvl;
    }

    /**
     * Set the value of lvl
     *
     * @param lvl new value of lvl
     */
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    /**
     * updates the class's properties
     * 
     */
    public void update() {
        //implement
    }
    
    /**
     * Manipulates gameStats of this character and enemy character according to 
     * battle calculations
     * 
     * @return the value of enemy character
     */
    public Character attack(Character enemy) {      
        //change stats of this according to battle calculations
        int newHPattacker = this.gameStats.getHp() - this.calculateDmg(this.gameStats, enemy.gameStats);
        this.gameStats.setHp(newHPattacker);
        
        //change stats of enemy according to battle calculations
        int newHPdefender = enemy.gameStats.getHp() - this.calculateDmg(enemy.gameStats, this.gameStats);
        enemy.gameStats.setHp(newHPdefender);
               
        return enemy;
    }
    
    /**
     * calculates damage done to defender according to battle formula
     * 
     * Formula: 
     * Atk - Def = dmg
     * 
     * @param s1 attacker's gamestats
     * @param s2 defender's gamestats
     * @return dmg done to defender
     */
    private int calculateDmg(Stats s1, Stats s2) {
        int dmg = 0;
        
        dmg = s1.getAtt() - s2.getDef();

//          //if unit's spd 5 more than enemy spd then dmg x2 <-- not yet needed for sprint
//        if (s1.getSpd() - s2.getSpd() >= 5) {
//            dmg = dmg * 2;
//        }   
        
        return dmg;
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
    
    /**
     * level increases and therefore growth increases based on level
     * 
     * @param amount amount of levels you want to levelup
     */
    public void levelUp(int amount){
        //implement basestat changes according to leveling system
    }
    
    /**
     * Rank changes and therefore growth changes based on rank
     * 
     */
    public void Promote(){
        //implement rank change depending on previous rank
    }
}
