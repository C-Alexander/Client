package works.maatwerk.generals.models;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class Vector {
    private int x;
    private int y;
    
    /**
     * 
     * @param x
     * @param y 
     */
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 
     * @return 
     */
    public int getX() {
        return x;
    }
    
    /**
     * 
     * @return 
     */
    public int getY() {
        return y;
    }
    
    /**
     * 
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * 
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Returns a new vector with x = x + 1 and y = y
     * @return 
     */
    public Vector addOneX() {
        return new Vector(x + 1, y);
    }
    
    /**
     * Returns a new vector with x = x and y = y + 1
     * @return 
     */
    public Vector addOneY() {
        return new Vector(x, y + 1);
    }
    
    /**
     * Returns a new vector with x = x - 1 and y = y
     * @return 
     */
    public Vector subOneX() {
        return new Vector(x - 1, y);
    }
    
    /**
     * Returns a new vector with x = x and y = y - 1
     * @return 
     */
    public Vector subOneY() {
        return new Vector(x, y - 1);
    }
}