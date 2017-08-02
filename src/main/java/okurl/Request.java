package okurl;

import okurl.util.Util;

/**
 * @author Ricky Fung
 */
public class Request {
    private final String url;
    private final HttpMethod method;
    private final Headers headers;
    private final RequestBody body;
    private final boolean cache;

    Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
        this.cache = builder.cache;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public boolean isCache() {
        return cache;
    }

    public static class Builder {
        private String url;
        private HttpMethod method;
        private Headers headers;
        private RequestBody body;
        private boolean cache;

        public Builder() {
            this.method = HttpMethod.GET;
            this.headers = new Headers();
        }

        public Builder url(String url) {
            this.url = url;
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

        public Builder get() {
            return method(HttpMethod.GET, null);
        }

        public Builder head() {
            return method(HttpMethod.HEAD, null);
        }

        public Builder post(RequestBody body) {
            return method(HttpMethod.POST, body);
        }

        public Builder delete(RequestBody body) {
            return method(HttpMethod.DELETE, body);
        }

        public Builder put(RequestBody body) {
            return method(HttpMethod.PUT, body);
        }

        public Builder patch(RequestBody body) {
            return method(HttpMethod.PATCH, body);
        }

        public Builder method(HttpMethod method, RequestBody body) {
            if (method == null)
                throw new NullPointerException("method == null");
            if (body != null && !Util.permitsRequestBody(method)) {
                throw new IllegalArgumentException("method " + method + " must not have a request body.");
            }
            if (body == null && Util.requiresRequestBody(method)) {
                throw new IllegalArgumentException("method " + method + " must have a request body.");
            }
            this.method = method;
            this.body = body;
            return this;
        }

        public Builder cache(boolean cache) {
            this.cache = cache;
            return this;
        }

        public Request build() {
            if (url == null) throw new IllegalStateException("url == null");
            return new Request(this);
        }
    }
}
