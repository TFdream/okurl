package okurl;
import okurl.internal.URLRequestFacade;
import java.io.IOException;

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

        //Interceptor
        return URLRequestFacade.newCall(originalRequest, client.connectTimeoutMillis(), client.readTimeoutMillis());
    }

    static RealCall newRealCall(OkURLClient client, Request originalRequest) {
        return new RealCall(client, originalRequest);
    }

    public boolean isExecuted() {
        return executed;
    }
}
