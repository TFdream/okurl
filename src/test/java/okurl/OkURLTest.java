package okurl;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class OkURLTest {

    private final OkURLClient client = new OkURLClient();

    @Test
    public void runGet() throws IOException {

        Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Cache-Control", "max-age=0")
                .build();

        Response response = client.execute(request);
        if (!response.isSuccessful())
            throw new IOException("Unexpected code:" + response.code());

        System.out.println(response.body().string());
    }

    @Test
    public void runAccessHeader() throws IOException {

        Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Cache-Control", "max-age=0")
                .build();

        Response response = client.execute(request);
        if (!response.isSuccessful())
            throw new IOException("Unexpected code:" + response.code());

        System.out.println("Server: " + response.header("Server"));
        System.out.println("Date: " + response.header("Date"));
        System.out.println("Expires: " + response.header("Expires"));
    }

    @Test
    @Ignore
    public void runPostForm() throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("name", "Ricky")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/submit.do")
                .post(formBody)
                .build();

        Response response = client.execute(request);
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }
}
