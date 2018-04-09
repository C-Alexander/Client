package works.maatwerk.generals.utils;

public class Settings {

    private static final String restUrl = "http://localhost:9000";
    private static final String wsUrl = "ws://localhost:9000";

    public static String getRestUrl() {
        return restUrl;
    }

    public static String getWsUrl() {
        return wsUrl;
    }

}
