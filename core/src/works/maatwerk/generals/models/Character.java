package works.maatwerk.generals.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Sam Dirkx
 */
public class Character {
    /**
     * Modifier which specifies the percentage of damage that can be done by a healing weapon (25 means 25% of the attack)
     */
    private static final int HEALER_DAMAGE_MODIFIER = 25;
    private final Stats baseStats;    //basisstats  
    private final Race race;
    private final String rank;
    private final List<Debuff> debuffs;
    private Weapon weapon;
    private int xp; //cannot exceed xp needed for next level (100*1.2^lvl)
    private int lvl;
    
    /**
     * 
     * @param baseStats
     * @param race
     * @param weapon
     * @param rank
     */
    public Character(Stats baseStats, Race race, Weapon weapon, String rank) {
        this.baseStats = baseStats;
        this.race = race;
        this.weapon = weapon;
        this.rank = rank;
        debuffs = new ArrayList<Debuff>();
    }
    
    /**
     * 
     * @return 
     */
    public Stats getBaseStats() {
        return baseStats;
    }
    
    /**
     * 
     * @return 
     */
    public Race getRace() {
        return race;
    }
    
    /**
     * 
     * @return 
     */
    public String getRank() {
        return rank;
    }
    
    /**
     * 
     * @return 
     */
    public Weapon getWeapon() {
        return weapon;
    }
    
    /**
     * 
     * @return 
     */
    public Stats getDebuffs() {
        Stats output = new Stats();
        for(Debuff d : debuffs) {
            output.addToThis(d.getStaticDebuff());
        }
        return output;
    }
    
    /**
     * 
     * @return 
     */
    public int getXp() {
        return xp;
    }
    
    /**
     * 
     * @return 
     */
    public int getLvl() {
        return lvl;
    }
    
    /**
     * 
     * @param weapon 
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    /**
     * 
     * @param debuff 
     */
    public void addDebuffs(Debuff debuff) {
        this.debuffs.add(debuff);
    }
    
    /**
     * 
     * @param xp 
     */
    public void setXp(int xp) {
        this.xp = xp;
    }
    
    /**
     * 
     * @param lvl 
     */
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    /**
     * Gets the stats of the character for this turn.
     * 
     * @return 
     */
    public Stats getGameStats() {
        Stats output = new Stats();
        output.addToThis(baseStats);
        output.addToThis(race.getStats());
        output.addToThis(weapon.getStats());
        output.addToThis(getDebuffs());
        return output;
    }
    
    /**
     * To check if this character is still alive
     * 
     * @return 
     */
    public boolean isAlive() {
        return getGameStats().getHealthPoints() > 0;
    }
    
    /**
     * Manipulates gameStats of this character and enemy character according to 
     * battle calculations
     * 
     * @param enemy
     */
    public void attack(Character enemy) {
        Stats enemyStats = enemy.getGameStats();
        Stats ownStats = this.getGameStats();
        int damageToEnemy = calculateDamage(weapon.isCanHeal(), enemyStats.getDefence(), ownStats.getAttack());
        int damageToSelf = calculateDamage(enemy.weapon.isCanHeal(), ownStats.getDefence(), enemyStats.getAttack());
        this.addDamageToCharacter(enemy, damageToEnemy);
        this.addDamageToCharacter(this, damageToSelf);
    }
    
    /**
     * Manipulates gameStats of this character according to healing calculations
     *
     * @param ally
     */
    public void heal(Character ally) {
        if(!weapon.isCanHeal())
            return;
        Stats added = new Stats();
        added.setHealthPoints(this.getGameStats().getAttack());
        ally.addDebuffs(new Debuff(-1, added, null));
    }
    
    /**
     * manipulates gameStats temporarily according to tile effect
     *
     * @param bonusTile
     */
    public void bonus(Tile bonusTile) {
        //implement
    }
    
    /**
     * updates the class's properties
     * 
     */
    public void update() {
        Iterator<Debuff> iterator = debuffs.iterator();
        while(iterator.hasNext()) {
            Debuff debuff = iterator.next();
            if(debuff.getTurns() == 0) {
                iterator.remove();
                continue;
            }
            debuff.update();
        }
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
    
    /**
     * Calculates the damage that will be done by the character.
     * 
     * @param weaponCanHeal
     * @param defence
     * @param attack
     * @return 
     */
    private int calculateDamage(boolean weaponCanHeal, int defence, int attack) {
        return defence - (weaponCanHeal ? ((attack * HEALER_DAMAGE_MODIFIER) / 100) : attack);
    }
    
    /**
     * Adds the damage the the debufflist for infinity (and beyond!)
     * 
     * @param character
     * @param damage 
     */
    private void addDamageToCharacter(Character character, int damage) {
        if(damage < 0) {
            Stats dmg = new Stats();
            dmg.setHealthPoints(damage);
            character.addDebuffs(new Debuff(-1, dmg, null));
        }
    }
}