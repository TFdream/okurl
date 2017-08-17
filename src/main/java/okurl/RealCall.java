package okurl;
import okurl.internal.URLRequestFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class RealCall implements Call {
    final OkURLClient client;
    final Request originalRequest;

    private volatile boolean executed;

    private RealCall(OkURLClient client, Request originalRequest) {
        this.client = client;
        this.originalRequest = originalRequest;
    }

    @Override
    public Request request() {
        return originalRequest;
    }

    @Override
    public Response execute() throws IOException {
        if (executed) {
            throw new IllegalStateException("Already Executed");
        }
        executed = true;

        //1、处理器拦截器的预处理（顺序执行）
        List<Interceptor> interceptors = client.interceptors();
        if (interceptors!=null) {
            for (int i = 0; i < interceptors.size(); i++) {
                interceptors.get(i).preHandle(originalRequest);
            }
        }

        //2. 发起请求
        Response response = URLRequestFacade.newCall(originalRequest, client.connectTimeoutMillis(), client.readTimeoutMillis());

        //3、处理器拦截器的后处理（逆序）
        if (interceptors != null) {
            for (int i = interceptors.size() - 1; i >= 0; i--) {
                interceptors.get(i).postHandle(originalRequest, response);
            }
        }
        return response;
    }

    static RealCall newRealCall(OkURLClient client, Request originalRequest) {
        return new RealCall(client, originalRequest);
    }

    public boolean isExecuted() {
        return executed;
    }
}
