package okurl.internal;

import okurl.util.StreamUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author Ricky Fung
 */
public class BufferedSource implements Closeable {
    final InputStream in;

    public BufferedSource(InputStream in) {
        this.in = in;
    }

    public byte[] readByteArray() throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream(1024);
            StreamUtils.copy(this.in, baos);
            return baos.toByteArray();
        } finally {
            StreamUtils.closeQuietly(baos);
        }
    }

    public String readString(Charset charset) throws IOException {
        byte[] bytes = readByteArray();
        return new String(bytes, charset);
    }

    @Override
    public void close() throws IOException {
        StreamUtils.close(this.in);
    }

}
