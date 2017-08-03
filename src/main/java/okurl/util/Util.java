package okurl.util;

import okurl.HttpMethod;
import okurl.internal.BufferedSource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public abstract class Util {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static final String UTF8 = "utf-8";

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

    public static Charset bomAwareCharset(BufferedSource source, Charset charset) {
        return charset;
    }

    public static String encode(String data) {
        try {
            return URLEncoder.encode(data, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("unknown charset:"+UTF8);
        }
    }

    public static String decode(String data) {
        try {
            return URLDecoder.decode(data, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("unknown charset:"+UTF8);
        }
    }
}
