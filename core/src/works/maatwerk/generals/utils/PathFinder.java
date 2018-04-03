package works.maatwerk.generals.utils;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public final class PathFinder {

    /**
     * Calculates the possiblities where a character can move according to the
     * field.
     *
     * Note: if tiles is not a square array it will throw a
     * illegalArgumentException.
     *
     * @param tiles Contains the movement cost of that particular field.
     * Negative values for impassible terrain.
     * @param character
     * @param x Position of the character in the array. This is for the row.
     * @param y Position of the character in the array. This is for the column.
     * @return
     */
    public static boolean[][] getPossibleMoves(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y) {
        return getPossibleMoves(tiles, character, x, y, 0);
    }

    /**
     * Calculates the possiblities where a character can attack according to the
     * field.
     *
     * Note: if tiles is not a square array it will throw a
     * illegalArgumentException.
     *
     * @param tiles Contains the attack range cost of that particular field.
     * Negative values for impassible terrain.
     * @param character
     * @param x Position of the character in the array. This is for the row.
     * @param y Position of the character in the array. This is for the column.
     * @return
     */
    public static boolean[][] getAttackRange(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y) {
        return getAttackRange(tiles, character, x, y, 0);
    }
    
    private static boolean[][] getAttackRange(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y, int rangeUsed) {
        boolean[][] output = getBooleanArray(tiles);
        if(x < 0 || y < 0 || x >= output.length || y >= output[0].length) {
            return output;
        }
        output[x][y] = true;
        int range = (character.getWeapon() == null ? 1 : character.getWeapon().getRange()) - rangeUsed;
        return output;
    }
    
    private static boolean[][] getPossibleMoves(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y, int movesUsed) {
        boolean[][] output = getBooleanArray(tiles);
        if(x < 0 || y < 0 || x >= output.length || y >= output[0].length) {
            return output;
        }
        output[x][y] = true;
        int moves = character.getGameStats().getMovement() - movesUsed;
        if (moves <= 0) {
            return output;
        }
        output = calcMove(tiles, character, x - 1, y, movesUsed, moves, output);
        output = calcMove(tiles, character, x + 1, y, movesUsed, moves, output);
        output = calcMove(tiles, character, x, y - 1, movesUsed, moves, output);
        return calcMove(tiles, character, x, y + 1, movesUsed, moves, output);
    }

    private static boolean[][] getBooleanArray(int[][] tiles) {
        int x = tiles.length;
        if (x < 1) {
            throw new IllegalArgumentException("Array with x length 0.");
        }
        int y = tiles[0].length;
        for (int i = 1; i < x; i++) {
            if (tiles[i].length != y) {
                throw new IllegalArgumentException("Array is not a square array.");
            }
        }
        return new boolean[x][y];
    }
    
    private static boolean[][] calcRange(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y, int rangeUsed, int range, boolean[][] array) {
        boolean[][] output = array;
        if(x < 0 || y < 0 || x >= output.length || y >= output[0].length) {
            return output;
        }
        if (tiles[x][y] >= 0 && tiles[x][y] <= range) {
            output = addBooleanArray(output, getAttackRange(tiles, character, x, y, rangeUsed + tiles[x][y]));
        }
        return output;
    }

    private static boolean[][] calcMove(int[][] tiles, works.maatwerk.generals.models.Character character, int x, int y, int movesUsed, int moves, boolean[][] array) {
        boolean[][] output = array;
        if(x < 0 || y < 0 || x >= output.length || y >= output[0].length) {
            return output;
        }
        if (tiles[x][y] >= 0 && tiles[x][y] <= moves) {
            output = addBooleanArray(output, getPossibleMoves(tiles, character, x, y, movesUsed + tiles[x][y]));
        }
        return output;
    }

    private static boolean[][] addBooleanArray(boolean[][] a, boolean[][] b) {
        boolean[][] output;
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array row count does not match");
        }
        output = new boolean[a.length][];
        for (int x = 0; x < a.length; x++) {
            if (a[x].length != b[x].length) {
                throw new IllegalArgumentException("Array column count does not match in row " + x);
            }
            output[x] = new boolean[a[x].length];
            for (int y = 0; y < a[x].length; y++) {
                output[x][y] = a[x][y] || b[x][y];
            }
        }
        return output;
    }
}