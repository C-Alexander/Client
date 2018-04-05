package works.maatwerk.generals.models;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class StatsTest {
    private Stats instance = new Stats();

    /**
     * Test of getHealthPoints method, of class Stats.
     */
    @Test
    public void testGetHealthPoints() {
        int expResult = 0;
        int result = instance.getHealthPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAttack method, of class Stats.
     */
    @Test
    public void testGetAttack() {
        int expResult = 0;
        int result = instance.getAttack();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDefence method, of class Stats.
     */
    @Test
    public void testGetDefence() {
        int expResult = 0;
        int result = instance.getDefence();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMovement method, of class Stats.
     */
    @Test
    public void testGetMovement() {
        int expResult = 0;
        int result = instance.getMovement();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpeed method, of class Stats.
     */
    @Test
    public void testGetSpeed() {
        int expResult = 0;
        int result = instance.getSpeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHealthPoints method, of class Stats.
     */
    @Test
    public void testSetHealthPoints() {
        int healthPoints = 10;
        instance.setHealthPoints(healthPoints);
        assertEquals(healthPoints, instance.getHealthPoints());
        instance.setHealthPoints(0);
        assertEquals(0, instance.getHealthPoints());
    }

    /**
     * Test of setAttack method, of class Stats.
     */
    @Test
    public void testSetAttack() {
        int attack = 10;
        instance.setAttack(attack);
        assertEquals(attack, instance.getAttack());
        instance.setAttack(0);
        assertEquals(0, instance.getAttack());
    }

    /**
     * Test of setDefence method, of class Stats.
     */
    @Test
    public void testSetDefence() {
        int defence = 10;
        instance.setDefence(defence);
        assertEquals(defence, instance.getDefence());
        instance.setDefence(0);
        assertEquals(0, instance.getDefence());
    }

    /**
     * Test of setMovement method, of class Stats.
     */
    @Test
    public void testSetMovement() {
        int movement = 10;
        instance.setMovement(movement);
        assertEquals(movement, instance.getMovement());
        instance.setMovement(0);
        assertEquals(0, instance.getMovement());
    }

    /**
     * Test of setSpeed method, of class Stats.
     */
    @Test
    public void testSetSpeed() {
        int speed = 10;
        instance.setSpeed(speed);
        assertEquals(speed, instance.getSpeed());
        instance.setSpeed(0);
        assertEquals(0, instance.getSpeed());
    }

    /**
     * Test of addToThis method, of class Stats.
     */
    @Test
    public void testAddToThis() {
        Stats stats = new Stats(10, 11, 12, 13, 14);
        Stats stats2 = new Stats(-10, -11, -12, -13, -14);
        instance.addToThis(stats);
        assertEquals(stats, instance);
        instance.addToThis(stats2);
        assertEquals(new Stats(), instance);
    }

    /**
     * Test of addToNew method, of class Stats.
     */
    @Test
    public void testAddToNew() {
        Stats stats = null;
        Stats stats2 = new Stats(1, 1, 1, 1, 1);
        Stats result = instance.addToNew(stats);
        assertEquals(instance, result);
        assertNotSame(instance, result);
        Stats result2 = result.addToNew(stats2);
        assertEquals(stats2, result2);
        assertNotSame(stats2, result2);
    }

    /**
     * Test of toString method, of class Stats.
     */
    @Test
    public void testToString() {
        String expResult = "hp=0  att=0  def=0  mvd=0  spd=0";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of cloneStats method, of class Stats.
     */
    @Test
    public void testCloneStats() {
        Stats result = instance.cloneStats();
        assertEquals(instance, result);
        assertNotSame(instance, result);
    }
}