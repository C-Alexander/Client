package works.maatwerk.generals.utils;

public class Settings {

    private static final String REST_URL = "http://dev.maatwerk.works";
    private static final String WS_URL = "ws://dev.maatwerk.works";

    private Settings() {
    }

    public static String getRestUrl() {
        return REST_URL;
    }

    public static String getWsUrl() {
        return WS_URL;
    }

}
