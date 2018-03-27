package works.maatwerk.generals.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by teund on 21/03/2018.
 */
public class Account {
    private String Username;
    private String Password;

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public Account(String username, String password) {
        Username = username;
        Password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "\"Username\":\"" + Username + "\"" +
                ",\"Password\":\"" + Password + "\"" +
                "}";
    }
}
