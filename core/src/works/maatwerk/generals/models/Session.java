package works.maatwerk.generals.models;

public class Session {
    private String id;
    private Account player;
    
    /**
     * 
     * @return 
     */
    public String getId() {
        return id;
    }
    
    /**
     * 
     * @return 
     */
    public Account getPlayer() {
        return player;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 
     * @param player 
     */
    public void setPlayer(Account player) {
        this.player = player;
    }
}