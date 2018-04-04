package works.maatwerk.generals.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import works.maatwerk.generals.models.Character;
import works.maatwerk.generals.models.Race;
import works.maatwerk.generals.models.Stats;
import works.maatwerk.generals.models.Weapon;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class PathFinderTest {
    private Race race = new Race("Test", new Stats(9, 3, 1, 0, 1));
    private Character character = new Character(race, 0, null, null);

    /**
     * Test of getPossibleMoves method, of class PathFinder.
     */
    @Test
    public void testGetPossibleMoves() {
        int[][] tiles = {
            {1, 1, 1, -1, -1, 1, 1},
            {1, 1, 1, 1, 1, 2, 1},
            {1, 1, -1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1,},
            {1, 1, 1, 1, -1, -1, 1},
            {1, 1, 1, 1, -1, -1, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };
        int x = 3;
        int y = 3;
        boolean[][] expResult = {
            {false, true, true, false, false, false, false},
            {true, true, true, true, true, true, true},
            {true, true, false, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, false, false, true},
            {true, true, true, true, false, false, true},
            {false, true, true, true, true, true, false}
        };
        boolean[][] result = PathFinder.getPossibleMoves(tiles, character, x, y);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getAttackRange method, of class PathFinder.
     */
    @Test
    public void testGetAttackRange() {
        character.setWeapon(new Weapon("Test weapon", 2, null, false, null));
        int[][] tiles = {
            {1, 1, 1, -1, -1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, -1, 2, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1,},
            {1, 1, 1, 1, -1, -1, 1},
            {1, 1, 1, 1, -1, -1, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };
        int x = 3;
        int y = 3;
        boolean[][] expResult = {
            {false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false},
            {false, false, false, true, true, false, false},
            {false, true, true, true, true, true, false},
            {false, false, true, true, false, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, false, false, false, false}
        };
        boolean[][] result = PathFinder.getAttackRange(tiles, character, x, y);
        assertArrayEquals(expResult, result);
    }
}