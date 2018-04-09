package works.maatwerk.generals.models;

/**
 * Created by teund on 21/03/2018.
 */
public class Account {
    private int id;
    private String username;
    private String password;
    
    /**
     * Default constructor
     */
    public Account() {
    }
    
    /**
     * 
     * @param username
     * @param password 
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * 
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * 
     * @return 
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * 
     * @return 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{"
                + "\"Username\":\"" + username + "\""
                + ",\"Password\":\"" + password + "\""
                + "}";
    }
}