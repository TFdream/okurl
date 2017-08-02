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
        out.write(b);
    }

    public void write(byte b[], int off, int len) throws IOException {
        out.write(b, off, len);
    }

    @Override
    public void close() throws IOException {
        StreamUtils.close(out);
    }
}
