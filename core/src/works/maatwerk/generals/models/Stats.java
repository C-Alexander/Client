package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Stats {
    private int hp;
    private int att;
    private int def;
    private int rng;
    private int mvd;
    private int spd;
    
    public Stats(int hp, int att, int def, int rng, int mvd, int spd) {
        this.hp = hp;
        this.att = att;
        this.def = def;
        this.rng = rng;
        this.mvd = mvd;
        this.spd = spd;
    }
    
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtt() {
        return att;
    }

    public void setAtt(int att) {
        this.att = att;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getRng() {
        return rng;
    }

    public void setRng(int rng) {
        this.rng = rng;
    }

    public int getMvd() {
        return mvd;
    }

    public void setMvd(int mvd) {
        this.mvd = mvd;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    @Override
    public String toString() {
        return "Stats{" + "hp=" + hp + ", att=" + att + ", def=" + def + ", rng=" + rng + ", mvd=" + mvd + ", spd=" + spd + '}';
    }
}
