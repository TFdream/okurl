package okurl;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class Response implements Closeable {
    Request request;
    int code;
    ResponseBody body;
    Headers headers;

    /** Returns the HTTP status code. */
    public int code() {
        return code;
    }

    /**
     * Returns true if the code is in [200..300), which means the request was successfully received,
     * understood, and accepted.
     */
    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public String header(String name) {
        return header(name, null);
    }

    public String header(String name, String defaultValue) {
        String result = headers.get(name);
        return result != null ? result : defaultValue;
    }

    public Headers headers() {
        return headers;
    }

    public ResponseBody body() {
        return body;
    }

    @Override
    public void close() throws IOException {

    }
}
