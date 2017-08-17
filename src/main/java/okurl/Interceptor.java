package okurl;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public interface Interceptor {

    void preHandle(Request request) throws IOException;

    void postHandle(Request request, Response response) throws IOException;
}
