package okurl.internal;

import okurl.util.StreamUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Ricky Fung
 */
public class BufferedSink implements Closeable {
    private final OutputStream out;

    public BufferedSink(OutputStream out) {
        this.out = out;
    }

    public void write(byte[] b) throws IOException {
        this.out.write(b);
    }

    public void write(byte b[], int off, int len) throws IOException {
        this.out.write(b, off, len);
    }

    public void flush() throws IOException {
        this.out.flush();;
    }

    @Override
    public void close() throws IOException {
        StreamUtils.close(this.out);
    }
}
