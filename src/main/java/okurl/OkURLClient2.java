package okurl;

import okurl.internal.URLRequestFacade;
import okurl.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class OkURLClient2 implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int connectTimeout;
    private final int writeTimeout;
    private final int readTimeout;

    public OkURLClient2() {
        this(new Builder());
    }

    public OkURLClient2(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.readTimeout = builder.readTimeout;
    }

    public Response execute(Request request) throws IOException {
        return URLRequestFacade.newCall(request, connectTimeout, readTimeout);
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

        public OkURLClient2 build() {
            return new OkURLClient2(this);
        }
    }
}
