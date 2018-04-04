package works.maatwerk.generals.models;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class WeaponTest {
    private String name = "Test";
    private int range = 2;
    private Stats stats = new Stats();
    private Debuff debuff = new Debuff(3, new Stats(), new Stats());
    private boolean canheal = true;
    private Weapon instance = new Weapon(name, range, stats, canheal, debuff);
    /**
     * Test of getName method, of class Weapon.
     */
    @Test
    public void testGetName() {
        String result = instance.getName();
        assertEquals(name, result);
    }

    /**
     * Test of getStats method, of class Weapon.
     */
    @Test
    public void testGetStats() {
        Stats result = instance.getStats();
        assertEquals(stats, result);
    }

    /**
     * Test of getStatusEffect method, of class Weapon.
     */
    @Test
    public void testGetStatusEffect() {
        Debuff result = instance.getStatusEffect();
        assertEquals(debuff, result);
    }

    /**
     * Test of isCanHeal method, of class Weapon.
     */
    @Test
    public void testIsCanHeal() {
        boolean result = instance.isCanHeal();
        assertEquals(canheal, result);
    }

    /**
     * Test of getRange method, of class Weapon.
     */
    @Test
    public void testGetRange() {
        int result = instance.getRange();
        assertEquals(range, result);
    }

    /**
     * Test of toString method, of class Weapon.
     */
    @Test
    public void testToString() {
        String expResult = "Weapon{" + "name=" + name + ", stats=" + stats + ", canHeal=" + canheal + ", range=" + range + '}';
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}