package teastapp.raid.util;

public class Time {
    public static long SECOND_MILLIS = 1000;
    public static long MINUTE_MILLIS = 60 * SECOND_MILLIS;

    public static long getNow() {
        return System.currentTimeMillis();
    }

    public static long timePassedFrom(long timeMillis) {
        return getNow() - timeMillis;
    }
    
    public static long timeLeftBefore(long timeMillis) {
        return timeMillis - getNow();
    }
}
