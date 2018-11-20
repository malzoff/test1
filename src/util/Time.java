package util;

public class Time {
    public static long SECOND_MILLIS = 1000;
    public static long MINUTE_MILLIS = 60 * 1000;

    public static long timePassedFrom(long timeMillis) {
        return System.currentTimeMillis() - timeMillis;
    }
    
    public static long timeLeftBefore(long startTimeMillis) {
        return startTimeMillis - System.currentTimeMillis();
    }

    public static long getNow() {
        return System.currentTimeMillis();
    }
}
