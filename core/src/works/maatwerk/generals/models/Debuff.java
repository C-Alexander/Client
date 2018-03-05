/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package works.maatwerk.generals.models;

/**
 *
 * @author Sam Dirkx
 */
public class Debuff {
    private int turns;
    private Stats staticDebuff;     //wat hetzelfde blijft
    private Stats dynamicDebuff;    //wat optelt
    
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

    public Debuff(int turns, Stats staticDebuff, Stats dynamicDebuff) {
        this.turns = turns;
        this.staticDebuff = staticDebuff;
        this.dynamicDebuff = dynamicDebuff;
    }

    @Override
    public String toString() {
        return "Debuff{" + "turns=" + turns + ", staticDebuff=" + staticDebuff + ", dynamicDebuff=" + dynamicDebuff + '}';
    }
}
