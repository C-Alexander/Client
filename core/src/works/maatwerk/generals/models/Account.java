package works.maatwerk.generals.models;

/**
 * Created by teund on 21/03/2018.
 */
public class Account {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account(String username, String password) {
        this.username = username;
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