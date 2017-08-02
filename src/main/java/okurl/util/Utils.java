package okurl.util;

import okurl.HttpMethod;

import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public abstract class Utils {

    public static int checkDuration(String name, long duration, TimeUnit unit) {
        if (duration < 0) throw new IllegalArgumentException(name + " < 0");
        if (unit == null) throw new NullPointerException("unit == null");
        long millis = unit.toMillis(duration);
        if (millis > Integer.MAX_VALUE) throw new IllegalArgumentException(name + " too large.");
        if (millis == 0 && duration > 0) throw new IllegalArgumentException(name + " too small.");
        return (int) millis;
    }

    public static boolean requiresRequestBody(HttpMethod method) {
        return method == HttpMethod.POST
                || method == HttpMethod.PUT
                || method == HttpMethod.PATCH;
    }

    public static boolean permitsRequestBody(HttpMethod method) {
        return requiresRequestBody(method) || method == HttpMethod.DELETE;

    }
}
