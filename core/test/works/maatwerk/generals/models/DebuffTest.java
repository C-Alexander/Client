package works.maatwerk.generals.models;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class DebuffTest {
    private Stats statics = new Stats(-1, -2, -2, 0, 0);
    private Stats dynamic = new Stats(-1, 0, 0, 0, 0);
    private Debuff instance = new Debuff(3, statics, dynamic);

    /**
     * Test of getTurns method, of class Debuff.
     */
    @Test
    public void testGetTurns() {
        int expResult = 3;
        int result = instance.getTurns();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStaticDebuff method, of class Debuff.
     */
    @Test
    public void testGetStaticDebuff() {
        Stats result = instance.getStaticDebuff();
        assertEquals(statics, result);
    }

    /**
     * Test of getDynamicDebuff method, of class Debuff.
     */
    @Test
    public void testGetDynamicDebuff() {
        Stats result = instance.getDynamicDebuff();
        assertEquals(dynamic, result);
    }

    /**
     * Test of update method, of class Debuff.
     */
    @Test
    public void testUpdate() {
        Debuff debuff = new Debuff(3, statics, dynamic);
        debuff.update();
        Stats stats = statics.addToNew(dynamic);
        assertEquals(stats, debuff.getStaticDebuff());
        debuff.update();
        stats.addToThis(dynamic);
        assertEquals(stats, debuff.getStaticDebuff());
        debuff.update();
        stats.addToThis(dynamic);
        assertEquals(stats, debuff.getStaticDebuff());
        debuff.update();
        assertEquals(stats, debuff.getStaticDebuff());
    }

    /**
     * Test of toString method, of class Debuff.
     */
    @Test
    public void testToString() {
        String expResult = "Debuff{" + "turns=3, staticDebuff=" + statics + ", dynamicDebuff=" + dynamic + '}';
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}