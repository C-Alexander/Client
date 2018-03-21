package works.maatwerk.generals.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by teund on 21/03/2018.
 */
public class Account {
    private String Username;
    private String Password;
    private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public Account(String username, String password) {
        Username = username;
        try {
            Password = getStringFromSHA256(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(this.toString());
    }

    public static String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for(final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    public static String getStringFromSHA256(String stringToEncrypt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }

    @Override
    public String toString() {
        return "{" +
                "\"Username\":\"" + Username + "\"" +
                ",\"Password\":\"" + Password + "\"" +
                "}";
    }
}
