package okurl;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class Response implements Closeable {
    final Request request;
    final int code;
    final String message;
    final ResponseBody body;
    final Headers headers;

    Response() {
        this(new Builder());
    }

    Response(Builder builder) {
        this.request = builder.request;
        this.code = builder.code;
        this.message = builder.message;
        this.body = builder.body;
        this.headers = builder.headers;
    }

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

    public static class Builder {
        Request request;
        int code;
        String message;
        ResponseBody body;
        Headers headers;

        public Builder() {
            code = -1;
            headers = new Headers();
        }
        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder header(String name, String value) {
            headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            headers.set(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Builder body(ResponseBody body) {
            this.body = body;
            return this;
        }

        public Response build() {
            if (request == null)
                throw new IllegalStateException("request == null");
            if (code < 0)
                throw new IllegalStateException("code < 0: " + code);
            if (message == null)
                throw new IllegalStateException("message == null");
            return new Response(this);
        }
    }
}
