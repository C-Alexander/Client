package works.maatwerk.generals.utils;

public class Settings {

    private static final String REST_URL = "http://localhost:9000";
    private static final String WS_URL = "ws://localhost:9000";

    private Settings() {
    }

    public static String getRestUrl() {
        return REST_URL;
    }

    public static String getWsUrl() {
        return WS_URL;
    }

}
