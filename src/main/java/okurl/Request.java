package okurl;

/**
 * @author Ricky Fung
 */
public class Request {

    Request(Builder builder) {
    }

    public static class Builder {
        private String url;
        private String method;
        private Headers headers;

        public Builder url(String url) {
            return this;
        }

        public Builder header(String name, String value) {
            headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            headers.set(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Request build() {
            if (url == null) throw new IllegalStateException("url == null");
            return new Request(this);
        }
    }
}
