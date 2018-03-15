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
    private static final int MAX_MINIONS = 3;
    private final Stats baseStats;
    private final Race race;
    private final Rank rank;
    private final List<Debuff> debuffs;
    private Weapon weapon;
    private List<Character> minions;
    
    /**
     * 
     * @param race
     */
    public Character(Race race) {
        this.baseStats = new Stats(50, 10, 5, 5, 1);
        this.race = race;
        this.rank = new Rank();
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
    public Rank getRank() {
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
    public List<Character> getMinions() {
       return minions; 
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
     * @param minion
     * @return 
     */
    public boolean addMinion(Character minion) {
        if(rank.getRankName() != RankName.HERO || minion.rank.getRankName() != RankName.GENERAL)
            throw new IllegalArgumentException();
        if(minions.size() >= MAX_MINIONS)
            return false;
        if(minions.contains(minion))
            return false;
        minions.add(minion);
        return true;
    }
    
    /**
     * 
     * @param minion
     * @return 
     */
    public boolean removeMinion(Character minion) {
       return minions.remove(minion);
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
        if(weapon != null) {
            output.addToThis(weapon.getStats());
        }
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
        int damageToEnemy = calculateDamage(((weapon == null) ? false : weapon.isCanHeal()), enemyStats.getDefence(), ownStats.getAttack());
        int damageToSelf = calculateDamage(((enemy.weapon == null) ? false : enemy.weapon.isCanHeal()), ownStats.getDefence(), enemyStats.getAttack());
        this.addDamageToCharacter(enemy, damageToEnemy);
        this.addDamageToCharacter(this, damageToSelf);
    }
    
    /**
     * Manipulates gameStats of this character according to healing calculations
     *
     * @param ally
     */
    public void heal(Character ally) {
        if(weapon == null)
            return;
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
     * For generals checks if the amount of grunts is correct otherwise spawns them.
     */
    public void matchStart() {
        if(rank.getRankName() != RankName.GENERAL)
            return;
        while(minions.size() <MAX_MINIONS) {
            minions.add(new Character(race));
        }
    }
    
    /**
     * Called when match has ended to clear debuff list and set experience.
     * Also returns list of minions which reached the same rank as their master character.
     * This function calls the same function in all its minions.
     * 
     * @return 
     */
    public List<Character> matchEnded() {
        if(!this.isAlive())
            return null;
        rank.update();
        debuffs.clear();
        return matchEndedMinions();
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
    
    /**
     * Calls matchEnded in all minions and checks of one of the minions gained the same rank as this character.
     * If same rank is achieved removes minion from the minion list and output it in the return list.
     * 
     * @return 
     */
    private List<Character> matchEndedMinions() {
        List<Character> output = new ArrayList<Character>();
        Iterator<Character> iterator = minions.iterator();
        while(iterator.hasNext()) {
            Character c = iterator.next();
            output.addAll(c.matchEnded());
            if(c.rank.getRankName() != rank.getRankName())
                continue;
            iterator.remove();
            c.minions.clear();
            output.add(c);
        }
        return output;
    }
}