package okurl.interceptor;

import okurl.Interceptor;
import okurl.Request;
import okurl.Response;
import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public void preHandle(Request request) throws IOException {
        System.out.println("preHandle...");
    }

    @Override
    public void postHandle(Request request, Response response) throws IOException {
        System.out.println("postHandle...");
    }
}
