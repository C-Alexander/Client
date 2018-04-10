package works.maatwerk.generals.utils;

import works.maatwerk.generals.models.Vector;
import works.maatwerk.generals.models.Weapon;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class PathFinder {
    
    private PathFinder() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Calculates the possiblities where a character can move according to the field.
     * 
     * Note: if tiles is not a square array it will throw a illegalArgumentException.
     * 
     * @param tiles Contains the movement cost of that particular field. Negative values for impassible terrain.
     * @param character
     * @param v
     * @return 
     */
    public static boolean[][] getPossibleMoves(int[][] tiles, works.maatwerk.generals.models.Character character, Vector v) {
        return toBooleanArray(genIntMap(true, tiles, character, v, getIntArray(tiles), 0, true));
    }

    /**
     * Calculates the possiblities where a character can attack according to the field.
     *
     * Note: if tiles is not a square array it will throw a illegalArgumentException.
     * 
     * @param tiles Contains the attack range cost of that particular field. Negative values for impassible terrain.
     * @param character
     * @param v
     * @return 
     */
    public static boolean[][] getAttackRange(int[][] tiles, works.maatwerk.generals.models.Character character, Vector v) {
        return toBooleanArray(genIntMap(false, tiles, character, v, getIntArray(tiles), 0, true));
    }
    
    private static Integer[][] genIntMap(boolean movement, int[][] tiles, works.maatwerk.generals.models.Character character, Vector v, Integer[][] input, int movesUsed, boolean start) {
	Integer[][] output = input;
        int weaponrange = getWeaponRange(character.getWeapon());
	int moves = getMovesLeft(movement, character.getGameStats().getMovement(), weaponrange, movesUsed);
        if(continueFunction(moves, v, output, tiles)) {
            return output;
        }
        int used = movesUsed + (start ? 0 : tiles[v.getX()][v.getY()]);
	if(tiles[v.getX()][v.getY()] > 0 && tiles[v.getX()][v.getY()] <= moves) {
            output[v.getX()][v.getY()] = moves - tiles[v.getX()][v.getY()];
            output = genIntMap(movement, tiles, character, v.addOneX(), output, used, false);
            output = genIntMap(movement, tiles, character, v.subOneX(), output, used, false);
            output = genIntMap(movement, tiles, character, v.addOneY(), output, used, false);
            output = genIntMap(movement, tiles, character, v.subOneY(), output, used, false);
	}
	return output;
    }
    
    private static int getWeaponRange(Weapon weapon) {
        if(weapon != null) {
            return weapon.getRange();
        }
        return 1;
    }
    
    private static int getMovesLeft(boolean movement, int moves, int range, int movesUsed) {
        int output;
        if(movement)
            output = moves;
        else
            output = range;
        return output - movesUsed;
    }
    
    private static boolean continueFunction(int moves, Vector v, Integer[][] array, int[][] tiles) {
        boolean outOfMoves = moves <= 0;
        boolean outOfBounds = v.getX() < 0 || v.getY() < 0 || v.getX() >= array.length || v.getY() >= array[0].length;
        boolean wouldNotImprove = array[v.getX()][v.getY()] != null && array[v.getX()][v.getY()] < (moves - tiles[v.getX()][v.getY()]);
        return outOfMoves || outOfBounds || wouldNotImprove;
    }
    
    private static Integer[][] getIntArray(int[][] tiles) {
        int x = tiles.length;
        if(x < 1)
            throw new IllegalArgumentException("Array with x length 0.");
        int y = tiles[0].length;
        for(int i = 1; i < x; i++) {
            if(tiles[i].length != y)
                throw new IllegalArgumentException("Array is not a square array.");
        }
        return new Integer[x][y];
    }
    
    private static boolean[][] toBooleanArray(Integer[][] array) {
        int i = array.length;
        int j = array[0].length;
        boolean[][] output = new boolean[i][j];
        for(int x = 0; x < i; x++) {
            for(int y = 0; y < j; y++) {
                if(isZeroOrPositive(array[x][y]))
                    output[x][y] = true;
            }
        }
        return output;
    }
    
    private static boolean isZeroOrPositive(Integer number) {
        return (number != null && number >= 0);
    }
}