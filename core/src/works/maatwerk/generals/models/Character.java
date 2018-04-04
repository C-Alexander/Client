package works.maatwerk.generals.models;

import works.maatwerk.generals.enums.RankName;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sam Dirkx
 */
@SuppressWarnings("WeakerAccess")
public class Character extends Actor {
    /**
     * Modifier which specifies the percentage of damage that can be done by a healing weapon (25 means 25% of the attack)
     */
    private static final int HEALER_DAMAGE_MODIFIER = 25;
    private static final int MAX_MINIONS = 3;
    private int id;
    private Stats baseStats;
    private Race race;
    private Rank rank;
    private List<Debuff> debuffs;
    private Weapon weapon;
    private List<Character> minions;
    private Vector2 location;
    private AssetManager assetManager;
    private int weaponClass;
    
    public Character() {
    }

    /**
     * Creates an instance of the Character class
     *
     * @param race
     * @param weaponclass
     * @param assetManager
     * @param location
     */
    public Character(Race race, int weaponclass, AssetManager assetManager, Vector2 location) {
        this.baseStats = new Stats(10, 5, 2, 5, 10);
        this.race = race;
        this.weaponClass = weaponclass;
        this.rank = new Rank();
        this.assetManager = assetManager;
        this.location = location;
        debuffs = new ArrayList<Debuff>(); //INFO: Houd de class in de diamond
        minions = new ArrayList<Character>(); //INFO: Houd de class in de diamond
    }
    
    /**
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * @return
     */
    public Stats getBaseStats() {
        return baseStats;
    }

    /**
     * @return
     */
    public Race getRace() {
        return race;
    }

    /**
     * @return
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @return
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * @return
     */
    public Stats getDebuffs() {
        Stats output = new Stats();
        for (Debuff d : debuffs) {
            output.addToThis(d.getStaticDebuff());
        }
        return output;
    }

    /**
     * @return
     */
    public List<Character> getMinions() {
        return minions;
    }
    
    /**
     * 
     * @return 
     */
    public Vector2 getLocation() {
        return location;
    }
    
    /**
     * 
     * @return 
     */
    public int getWeaponClass() {
        return weaponClass;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @param weapon
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    /**
     * 
     * @param location 
     */
    public void setLocation(Vector2 location) {
        this.location = location;
    }
    
    /**
     * 
     * @param weaponClass 
     */
    public void setWeaponClass(int weaponClass) {
        this.weaponClass = weaponClass;
    }

    /**
     * @param debuff
     */
    public void addDebuffs(Debuff debuff) {
        this.debuffs.add(debuff);
    }

    /**
     * @param minion
     * @return
     */
    public boolean addMinion(Character minion) {
        if (rank.getRankName() != RankName.HERO || minion.rank.getRankName() != RankName.GENERAL)
            throw new IllegalArgumentException();
        if (minions.size() >= MAX_MINIONS)
            return false;
        if (minions.contains(minion))
            return false;
        minions.add(minion);
        return true;
    }

    /**
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
        if (weapon != null) {
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
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
        if (!enemy.equals(this)) {
            Stats enemyStats = enemy.getGameStats();
            Stats ownStats = this.getGameStats();
            int damageToEnemy = calculateDamage(((weapon != null) && weapon.isCanHeal()), enemyStats, enemy.weaponClass, ownStats, this.weaponClass);
            int damageToSelf = calculateDamage(((enemy.weapon != null) && enemy.weapon.isCanHeal()), ownStats, this.weaponClass, enemyStats, enemy.weaponClass);
            this.addDamageToCharacter(enemy, damageToEnemy);
            if(enemy.isAlive())
            this.addDamageToCharacter(this, damageToSelf);
        }
    }

    /**
     * Manipulates gameStats of this character according to healing calculations
     *
     * @param ally
     */
    public void heal(Character ally) {
        if (weapon == null)
            return;
        if (!weapon.isCanHeal())
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
    @SuppressWarnings("EmptyMethod")
    public void bonus(Tile bonusTile) {
        //implement
    }

    /**
     * updates the class's properties
     */
    public void update() {
        Iterator<Debuff> iterator = debuffs.iterator();
        while (iterator.hasNext()) {
            Debuff debuff = iterator.next();
            if (debuff.getTurns() == 0) {
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
        if (rank.getRankName() != RankName.GENERAL)
            return;
        while (minions.size() < MAX_MINIONS) {
            minions.add(new Character(race, this.weaponClass, this.assetManager, this.location));
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
        if (!this.isAlive())
            return new ArrayList<Character>();
        rank.update();
        debuffs.clear();
        return matchEndedMinions();
    }
    
    public Texture getTexture() {
        String prefix;
        String clazz;
        switch(this.rank.getRankName()) {
            case GRUNT:
                prefix = "m";
                break;
            case GENERAL:
                prefix = "g";
                break;
            case HERO:
                prefix = "h";
                break;
            default:
                return null;
        }
        switch(this.weaponClass) {
            case WeaponClass.ARCANE:
                clazz = "Arcane";
                break;
            case WeaponClass.AXE:
                clazz = "Axe";
                break;
            case WeaponClass.BOW:
                clazz = "Bow";
                break;
            case WeaponClass.CORRUPT:
                clazz = "Corrupt";
                break;
            case WeaponClass.DIVINE:
                clazz = "Divine";
                break;
            case WeaponClass.HEALER:
                clazz = "Healer";
                break;
            case WeaponClass.SPEAR:
                clazz = "Spear";
                break;
            case WeaponClass.SWORD:
                clazz = "Sword";
                break;
            case WeaponClass.VALKYRIE:
                clazz = "Valkyrie";
                break;
            default:
                return null;
        }
        return assetManager.get("characters/" + prefix + clazz + ".png");
    }
    
    /**
     * Calculates the damage that will be done by the character.
     *
     * @param weaponCanHeal
     * @param defence
     * @param attack
     * @return
     */
    private int calculateDamage(boolean weaponCanHeal, Stats defence, int weaponClassDefence, Stats attack, int weaponClassAttack) {
        return ((defence.getDefence() - (weaponCanHeal ? ((attack.getAttack() * HEALER_DAMAGE_MODIFIER) / 100) : attack.getAttack())) * WeaponClass.getWeaponModifier(weaponClassAttack, weaponClassDefence)) / 100;
    }

    /**
     * Adds the damage the the debufflist for infinity (and beyond!)
     *
     * @param character
     * @param damage
     */
    private void addDamageToCharacter(Character character, int damage) {
        if (damage < 0) {
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
        while (iterator.hasNext()) {
            Character c = iterator.next();
            output.addAll(c.matchEnded());
            if (c.rank.getRankName() != rank.getRankName())
                continue;
            iterator.remove();
            c.minions.clear();
            output.add(c);
        }
        return output;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(this.getTexture(), this.getLocation().x * 32, this.getLocation().y * 32);
    }
    
    @Override
    public String getName() {
        return super.getName() != null ? super.getName() : "Generic Unit";
    }
}