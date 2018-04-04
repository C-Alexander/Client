package works.maatwerk.generals.models;

import org.junit.Test;
import static org.junit.Assert.*;
import works.maatwerk.generals.enums.RankName;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class RankTest {
    private Rank instance = new Rank();

    /**
     * Test of getRankName method, of class Rank.
     */
    @Test
    public void testGetRankName() {
        RankName expResult = RankName.GRUNT;
        RankName result = instance.getRankName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMatchExperience method, of class Rank.
     */
    @Test
    public void testGetMatchExperience() {
        int expResult = 0;
        int result = instance.getMatchExperience();
        assertEquals(expResult, result);
    }

    /**
     * Test of getExperience method, of class Rank.
     */
    @Test
    public void testGetExperience() {
        int expResult = 0;
        int result = instance.getExperience();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLevel method, of class Rank.
     */
    @Test
    public void testGetLevel() {
        int expResult = 0;
        int result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getExperienceCap method, of class Rank.
     */
    @Test
    public void testGetExperienceCap() {
        int expResult = (int) (100 * Math.pow(1.2, 0));
        int result = instance.getExperienceCap();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLevelCap method, of class Rank.
     */
    @Test
    public void testGetLevelCap() {
        int expResult = 10;
        int result = instance.getLevelCap();
        assertEquals(expResult, result);
    }

    /**
     * Test of addExperienceThroughDamageDealt method, of class Rank.
     */
    @Test
    public void testAddExperienceThroughDamageDealt() {
        int damage = 12;
        Rank rank = new Rank();
        rank.addExperienceThroughDamageDealt(damage);
        assertEquals(9, rank.getMatchExperience());
    }

    /**
     * Test of addExperienceThroughDamageTaken method, of class Rank.
     */
    @Test
    public void testAddExperienceThroughDamageTaken() {
        int damage = 10;
        Rank rank = new Rank();
        rank.addExperienceThroughDamageTaken(damage);
        assertEquals(5, rank.getMatchExperience());
    }

    /**
     * Test of update method, of class Rank.
     */
    @Test
    public void testUpdate() {
        Rank rank = new Rank();
        rank.addExperienceThroughDamageDealt(134); // 134 * 75 / 100 = 100 level up from level 0 to level 1
        assertEquals(100, rank.getMatchExperience());
        rank.update();
        assertEquals(1, rank.getLevel());
        assertEquals(0, rank.getMatchExperience());
        rank.addExperienceThroughDamageDealt(4284); // 4284 * 75 / 100 = 3213 level up from level 1 to level 1 next rank (general)
        assertEquals(3213, rank.getMatchExperience());
        rank.update();
        assertEquals(RankName.GENERAL, rank.getRankName());
        assertEquals(1, rank.getLevel());
        assertEquals(3, rank.getExperience());
    }
}