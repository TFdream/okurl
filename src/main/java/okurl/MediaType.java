package okurl;

import java.nio.charset.Charset;

/**
 * @author Ricky Fung
 */
public enum MediaType {
    MEDIA_TYPE_PNG("image/png", "image", "png", null);
    private final String mediaType;
    private final String type;
    private final String subtype;
    private final String charset;

    MediaType(String mediaType, String type, String subtype, String charset) {
        this.mediaType = mediaType;
        this.type = type;
        this.subtype = subtype;
        this.charset = charset;
    }

    public static MediaType parse(String string) {
        return null;
    }

    public String type() {
        return type;
    }

    public String subtype() {
        return subtype;
    }

    public Charset charset() {
        return charset(null);
    }

    public Charset charset(Charset defaultValue) {
        try {
            return charset != null ? Charset.forName(charset) : defaultValue;
        } catch (IllegalArgumentException e) {
            return defaultValue; // This charset is invalid or unsupported. Give up.
        }
    }
}
