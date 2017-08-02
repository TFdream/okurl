package okurl;

import okurl.internal.BufferedSource;
import okurl.util.StreamUtils;
import okurl.util.Util;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Ricky Fung
 */
public class ResponseBody implements Closeable {

    final BufferedSource source;
    final long contentLength;
    final String contentType;

    ResponseBody() {
        this(new Builder());
    }

    ResponseBody(Builder builder) {
        this.source = builder.source;
        this.contentLength = builder.contentLength;
        this.contentType = builder.contentType;
    }

    public BufferedSource source() {
        return source;
    }

    public long getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public final byte[] bytes() throws IOException {
        long contentLength = getContentLength();
        if (contentLength > Integer.MAX_VALUE) {
            throw new IOException("Cannot buffer entire body for content length: " + contentLength);
        }

        BufferedSource stream = source();
        byte[] bytes;
        try {
            bytes = stream.readByteArray();
        } finally {
            StreamUtils.closeQuietly(stream);
        }
        if (contentLength != -1 && contentLength != bytes.length) {
            throw new IOException("Content-Length ("
                    + contentLength
                    + ") and stream length ("
                    + bytes.length
                    + ") disagree");
        }
        return bytes;
    }

    public final String string() throws IOException {
        BufferedSource stream = source();
        try {
            Charset charset = Util.bomAwareCharset(stream, charset());
            return stream.readString(charset);
        } finally {
            StreamUtils.closeQuietly(stream);
        }
    }

    private Charset charset() {
        return Util.UTF_8;
    }

    @Override
    public void close() throws IOException {
        StreamUtils.closeQuietly(source());
    }

    public static class Builder {
        private BufferedSource source;
        private long contentLength;
        private String contentType;

        public Builder() {
            this.contentLength = -1;
        }

        public Builder source(BufferedSource source) {
            this.source = source;
            return this;
        }

        public Builder contentLength(long contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public ResponseBody build() {
            return new ResponseBody(this);
        }
    }
}
