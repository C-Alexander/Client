package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Debuff {
    private int turns;
    private Stats staticDebuff;     //wat hetzelfde blijft
    private Stats dynamicDebuff;    //wat optelt
    
    public Debuff(int turns, Stats staticDebuff, Stats dynamicDebuff) {
        this.turns = turns;
        this.staticDebuff = staticDebuff;
        this.dynamicDebuff = dynamicDebuff;
    }
    
    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
    
    public Stats getStaticDebuff() {
        return staticDebuff;
    }

    public void setStaticDebuff(Stats staticDebuff) {
        this.staticDebuff = staticDebuff;
    }

    public Stats getDynamicDebuff() {
        return dynamicDebuff;
    }

    public void setDynamicDebuff(Stats dynamicDebuff) {
        this.dynamicDebuff = dynamicDebuff;
    }

    @Override
    public String toString() {
        return "Debuff{" + "turns=" + turns + ", staticDebuff=" + staticDebuff + ", dynamicDebuff=" + dynamicDebuff + '}';
    }
}
