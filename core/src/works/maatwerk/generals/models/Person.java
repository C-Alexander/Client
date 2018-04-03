package works.maatwerk.generals.models;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class Person {
    private String teun = "teun";
    private Boolean teLaat = true;
    private int triggerLevel = 9001;

    public String getTeun() {
        return teun;
    }

    public void setTeun(String teun) {
        this.teun = teun;
    }

    public Boolean getTeLaat() {
        return teLaat;
    }

    public void setTeLaat(Boolean teLaat) {
        this.teLaat = teLaat;
    }

    public int getTriggerLevel() {
        return triggerLevel;
    }

    public void setTriggerLevel(int triggerLevel) {
        this.triggerLevel = triggerLevel;
    }
}