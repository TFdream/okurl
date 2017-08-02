package okurl;

import okurl.internal.BufferedStream;
import okurl.util.StreamUtils;
import okurl.util.Util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author Ricky Fung
 */
public class ResponseBody implements Closeable {

    final BufferedStream stream;
    final long contentLength;
    final String contentType;

    ResponseBody() {
        this(new Builder());
    }

    ResponseBody(Builder builder) {
        this.stream = builder.stream;
        this.contentLength = builder.contentLength;
        this.contentType = builder.contentType;
    }

    public BufferedStream stream() {
        return stream;
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

        BufferedStream stream = stream();
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
        BufferedStream stream = stream();
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
        StreamUtils.closeQuietly(stream());
    }

    public static class Builder {
        private BufferedStream stream;
        private long contentLength;
        private String contentType;

        public Builder() {
            this.contentLength = -1;
        }

        public Builder stream(InputStream in) {
            this.stream = new BufferedStream(in);
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
