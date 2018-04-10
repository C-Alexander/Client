package works.maatwerk.generals.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import works.maatwerk.generals.models.Character;
import works.maatwerk.generals.models.Race;
import works.maatwerk.generals.models.Rank;
import works.maatwerk.generals.models.Stats;
import works.maatwerk.generals.models.Vector;
import works.maatwerk.generals.models.Weapon;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class PathFinderTest {
    private final Race race = new Race("Test", new Stats(9, 3, 1, 0, 1));
    private final Character character = new Character(race, new Rank(), 0, null, null);

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
        boolean[][] result = PathFinder.getPossibleMoves(tiles, character, new Vector(x, y));
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
        boolean[][] result = PathFinder.getAttackRange(tiles, character, new Vector(x, y));
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of getPossibleMoves method, of class PathFinder.
     */
    @Test
    public void testGetPossibleMovesBig() {
        Race r = new Race("Test", new Stats(9, 3, 1, 5, 1));
        Character c = new Character(r, new Rank(), 0, null, null);
        int sides = 100;
        int posX = 50;
        int posY = 50;
        int[][] tiles = new int[sides + 1][sides + 1];
        boolean[][] expResult = new boolean[sides + 1][sides + 1];
        for(int x = 0; x <= sides; x++) {
            for(int y = 0; y <= sides; y++) {
                tiles[x][y] = 1;
                if(Math.abs(x - posX) + Math.abs(y - posY) <= 10) {
                    expResult[x][y] = true;
                }
            }
        }
        boolean[][] result = PathFinder.getPossibleMoves(tiles, c, new Vector(posX, posY));
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of getPossibleMoves method, of class PathFinder.
     */
    @Test
    public void testGetAttackRangeBig() {
        Race r = new Race("Test", new Stats(9, 3, 1, 5, 1));
        Character c = new Character(r, new Rank(), 0, null, null);
        c.setWeapon(new Weapon("Test weapon", 10, null, false, null));
        int sides = 100;
        int posX = 50;
        int posY = 50;
        int[][] tiles = new int[sides + 1][sides + 1];
        boolean[][] expResult = new boolean[sides + 1][sides + 1];
        for(int x = 0; x <= sides; x++) {
            for(int y = 0; y <= sides; y++) {
                tiles[x][y] = 1;
                if(Math.abs(x - posX) + Math.abs(y - posY) <= 10) {
                    expResult[x][y] = true;
                }
            }
        }
        boolean[][] result = PathFinder.getAttackRange(tiles, c, new Vector(posX, posY));
        assertArrayEquals(expResult, result);
    }
}