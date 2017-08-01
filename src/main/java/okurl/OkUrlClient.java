package okurl;

import okurl.util.Utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class OkUrlClient implements Closeable {

    public OkUrlClient() {
        this(new Builder());
    }

    public OkUrlClient(Builder builder) {

    }

    public Response execute(Request request) throws IOException {
        return URLConnectionProxy.newCall(request);
    }

    @Override
    public void close() throws IOException {

    }

    public static class Builder {
        private int connectTimeout;
        private int writeTimeout;
        private int readTimeout;

        public Builder() {

        }

        public Builder connectTimeout(long timeout, TimeUnit unit) {
            connectTimeout = Utils.checkDuration("timeout", timeout, unit);
            return this;
        }

        public Builder readTimeout(long timeout, TimeUnit unit) {
            readTimeout = Utils.checkDuration("timeout", timeout, unit);
            return this;
        }

        public Builder writeTimeout(long timeout, TimeUnit unit) {
            writeTimeout = Utils.checkDuration("timeout", timeout, unit);
            return this;
        }

        public OkUrlClient build() {
            return new OkUrlClient(this);
        }
    }
}
