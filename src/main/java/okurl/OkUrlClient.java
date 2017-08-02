package okurl;

import okurl.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class OkUrlClient implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int connectTimeout;
    private final int writeTimeout;
    private final int readTimeout;

    public OkUrlClient() {
        this(new Builder());
    }

    public OkUrlClient(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.readTimeout = builder.readTimeout;
        this.init();
    }

    private void init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeout));
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(writeTimeout));
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
            this.connectTimeout = 10 * 1000;
            this.writeTimeout = 10 * 1000;
            this.readTimeout = 10 * 1000;
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
