package okurl;

import okurl.interceptor.LoggingInterceptor;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class InterceptorTest {

    private final OkURLClient client = new OkURLClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();

    @Test
    @Ignore
    public void testInterceptor() throws IOException {

        Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Cache-Control", "max-age=0")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code:" + response.code());

        System.out.println(response.body().string());
    }
}
