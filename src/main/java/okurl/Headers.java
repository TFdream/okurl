package okurl;

import java.util.*;

/**
 * @author Ricky Fung
 */
public class Headers {
    private Map<String, String> headers;

    Headers() {}

    public void set(String name, String value) {
        if(headers==null) {
            this.headers = new HashMap<>(8);
        }
        this.headers.put(name, value);
    }

    public void remove(String name) {
        if(this.headers==null) {
            return;
        }
        this.headers.remove(name);
    }

    public String get(String name) {
        if(this.headers==null) {
            return null;
        }
        return this.headers.get(name);
    }

    /** Returns an immutable case-insensitive set of header names. */
    public Set<String> names() {
        if(this.headers==null) {
            return Collections.emptySet();
        }
        TreeSet<String> result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        result.addAll(headers.keySet());
        return Collections.unmodifiableSet(result);
    }

    public Map<String, String> headerFields() {
        if(this.headers==null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(headers);
    }
}
