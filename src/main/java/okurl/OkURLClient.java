package okurl;

import okurl.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class OkURLClient implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int connectTimeout;
    private final int writeTimeout;
    private final int readTimeout;

    public OkURLClient() {
        this(new Builder());
    }

    public OkURLClient(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.readTimeout = builder.readTimeout;
    }

    /** Default connect timeout (in milliseconds). */
    public int connectTimeoutMillis() {
        return connectTimeout;
    }

    /** Default read timeout (in milliseconds). */
    public int readTimeoutMillis() {
        return readTimeout;
    }

    /** Default write timeout (in milliseconds). */
    public int writeTimeoutMillis() {
        return writeTimeout;
    }

    public Call newCall(Request request) {
        return RealCall.newRealCall(this, request);
    }

    @Override
    public void close() throws IOException {
        destroy();
    }

    public void destroy() {
        logger.debug("destroy");
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
            connectTimeout = Util.checkDuration("timeout", timeout, unit);
            return this;
        }

        public Builder readTimeout(long timeout, TimeUnit unit) {
            readTimeout = Util.checkDuration("timeout", timeout, unit);
            return this;
        }

        public Builder writeTimeout(long timeout, TimeUnit unit) {
            writeTimeout = Util.checkDuration("timeout", timeout, unit);
            return this;
        }

        public OkURLClient build() {
            return new OkURLClient(this);
        }
    }
}
