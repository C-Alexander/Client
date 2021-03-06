package works.maatwerk.generals.models;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class WeaponClass {
    public static final int NO_CLASS = 0x00000000;
    public static final int SWORD    = 0x00000001;
    public static final int AXE      = 0x00000002;
    public static final int SPEAR    = 0x00000004;
    public static final int DIVINE   = 0x00000008;
    public static final int CORRUPT  = 0x00000010;
    public static final int ARCANE   = 0x00000020;
    public static final int BOW      = 0x00000040;
    public static final int HEALER   = 0x00000080;
    public static final int VALKYRIE = 0x00000100;
    
    private WeaponClass() {
        throw new IllegalStateException("Utility class");
    }
    
    public static int getWeaponModifier(int self, int enemy) {
        int output = 100;
        if(((self & SWORD) > 0 && (enemy & AXE) > 0)) {
            output = 120;
        } else if(((self & AXE) > 0 && (enemy & SPEAR) > 0)) {
            output = 120;
        } else if(((self & SPEAR) > 0 && (enemy & SWORD) > 0)) {
            output = 120;
        } else if(((self & DIVINE) > 0 && (enemy & CORRUPT) > 0)) {
            output = 120;
        } else if(((self & CORRUPT) > 0 && (enemy & ARCANE) > 0)) {
            output = 120;
        } else if(((self & ARCANE) > 0 && (enemy & DIVINE) > 0)) {
            output = 120;
        } else if(((self & BOW) > 0 && (enemy & HEALER) > 0)) {
            output = 120;
        }
        return output;
    }
}