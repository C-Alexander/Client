package works.maatwerk.generals.utils;

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
     * @param x Position of the character in the array. This is for the row.
     * @param y Position of the character in the array. This is for the column.
     * @return 
     */
    public static boolean[][] getPossibleMoves(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y) {
        return toBooleanArray(genIntMap(true, tiles, character, x, y, getIntArray(tiles), 0, true));
    }

    /**
     * Calculates the possiblities where a character can attack according to the field.
     *
     * Note: if tiles is not a square array it will throw a illegalArgumentException.
     * 
     * @param tiles Contains the attack range cost of that particular field. Negative values for impassible terrain.
     * @param character
     * @param x Position of the character in the array. This is for the row.
     * @param y Position of the character in the array. This is for the column.
     * @return 
     */
    public static boolean[][] getAttackRange(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y) {
        return toBooleanArray(genIntMap(false, tiles, character, x, y, getIntArray(tiles), 0, true));
    }
    
    private static Integer[][] genIntMap(boolean movement, int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y, Integer[][] input, int movesUsed, boolean start) {
	Integer[][] output = input;
	int moves = (movement ? character.getGameStats().getMovement() : (character.getWeapon() == null ? 1 : character.getWeapon().getRange())) - movesUsed;
	//Moves used up
        if (moves <= 0) {
            return output;
        }
	//Outside array
        if(x < 0 || y < 0 || x >= output.length || y >= output[0].length) {
            return output;
        }
	boolean improve = output[x][y] == null ? true : output[x][y] < (moves - tiles[x][y]);
	if(tiles[x][y] > 0 && tiles[x][y] <= moves && improve) {
            output[x][y] = moves - tiles[x][y];
            int used = movesUsed + (start ? 0 : tiles[x][y]);
            output = genIntMap(movement, tiles, character, x + 1, y, output, used, false);
            output = genIntMap(movement, tiles, character, x - 1, y, output, used, false);
            output = genIntMap(movement, tiles, character, x, y + 1, output, used, false);
            output = genIntMap(movement, tiles, character, x, y - 1, output, used, false);
	}
	return output;
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