package okurl;

import java.util.*;

/**
 * @author Ricky Fung
 */
public class Headers {
    private Map<String, String> headers;

    Headers() {
        this.headers = new HashMap<>(8);
    }

    public void set(String name, String value) {
        this.headers.put(name, value);
    }

    public void remove(String name) {
        this.headers.remove(name);
    }

    public String get(String name) {
        return this.headers.get(name);
    }

    /** Returns an immutable case-insensitive set of header names. */
    public Set<String> names() {
        TreeSet<String> result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        result.addAll(headers.keySet());
        return Collections.unmodifiableSet(result);
    }

}
