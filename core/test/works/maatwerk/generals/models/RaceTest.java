package works.maatwerk.generals.models;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class RaceTest {
    private String name = "Human";
    private Stats stats = new Stats();
    private Race instance = new Race(name, stats);

    /**
     * Test of getName method, of class Race.
     */
    @Test
    public void testGetName() {
        String result = instance.getName();
        assertEquals(name, result);
    }

    /**
     * Test of getStats method, of class Race.
     */
    @Test
    public void testGetStats() {
        Stats result = instance.getStats();
        assertEquals(stats, result);
    }

    /**
     * Test of toString method, of class Race.
     */
    @Test
    public void testToString() {
        String expResult = "Race{" + "name=" + name + ", stats=" + stats + '}';
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}