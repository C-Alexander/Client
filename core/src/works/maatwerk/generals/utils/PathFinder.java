package works.maatwerk.generals.utils;

import works.maatwerk.generals.models.Vector;

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
        int weaponrange = 1;
        if(character.getWeapon() != null) {
            weaponrange = character.getWeapon().getRange();
        }
	int moves = (movement ? character.getGameStats().getMovement() : weaponrange) - movesUsed;
        if(x(moves, v, output.length, output[0].length)) {
            return output;
        }
	//boolean improve = output[v.getX()][v.getY()] == null ? true : output[v.getX()][v.getY()] < (moves - tiles[v.getX()][v.getY()]);
        int used = movesUsed + (start ? 0 : tiles[v.getX()][v.getY()]);
        boolean improve = output[v.getX()][v.getY()] < (moves - tiles[v.getX()][v.getY()]);
	if(tiles[v.getX()][v.getY()] > 0 && tiles[v.getX()][v.getY()] <= moves && improve) {
            output[v.getX()][v.getY()] = moves - tiles[v.getX()][v.getY()];
            output = genIntMap(movement, tiles, character, v.addOneX(), output, used, false);
            output = genIntMap(movement, tiles, character, v.subOneX(), output, used, false);
            output = genIntMap(movement, tiles, character, v.addOneY(), output, used, false);
            output = genIntMap(movement, tiles, character, v.subOneY(), output, used, false);
	}
	return output;
    }
    
    private static boolean x(int moves, Vector v, int lengthX, int lengthY) {
        boolean outOfMoves = moves <= 0;
        boolean outOfBounds = v.getX() < 0 || v.getY() < 0 || v.getX() >= lengthX || v.getY() >= lengthY;
        return outOfMoves || outOfBounds;
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
                if(array[x][y] != null && array[x][y] >= 0)
                    output[x][y] = true;
            }
        }
        return output;
    }
}