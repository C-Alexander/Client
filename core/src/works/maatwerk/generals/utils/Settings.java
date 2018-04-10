package works.maatwerk.generals.utils;

import java.util.concurrent.locks.ReentrantLock;

public class Settings {
    /**
     * Will be initialized with commandline arguments in the future
     */

    public static final String REST_DEV_URL = "http://dev.maatwerk.works";
    public static final String WS_DEV_URL = "ws://dev.maatwerk.works";
    public static final String REST_PROD_URL = "http://prod.maatwerk.works";
    public static final String WS_PROD_URL = "ws://prod.maatwerk.works";
    public static final String REST_LOCAL_URL = "http://localhost:9000";
    public static final String WS_LOCAL_URL = "ws://localhost:9000";
    private static final ReentrantLock wsLock = new ReentrantLock();
    private static final ReentrantLock restLock = new ReentrantLock();


    private static String REST_URL = REST_DEV_URL;
    private static String WS_URL = WS_DEV_URL;


    private Settings() {
        throw new IllegalStateException("Utility class");
    }

    public static String getRestUrl() {
        restLock.lock();
        try {
            return REST_URL;
        } finally {
            restLock.unlock();
        }
    }

    public static void setRestUrl(String restUrl) {
        restLock.lock();
        try {
            REST_URL = restUrl;
        } finally {
            restLock.unlock();
        }
    }

    public static String getWsUrl() {
        wsLock.lock();
        try {
            return WS_URL;
        } finally {
            wsLock.unlock();
        }
    }

    public static void setWsUrl(String wsUrl) {
        wsLock.lock();
        try {
            WS_URL = wsUrl;
        } finally {
            wsLock.unlock();
        }
    }
}