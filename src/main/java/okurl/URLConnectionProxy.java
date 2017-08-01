package okurl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Ricky Fung
 */
public class URLConnectionProxy {

    public static Response newCall(Request request) throws IOException {
        try {
            //1.
            URL url = new URL("http://localhost:8080/index.jsp");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //2. 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            urlConnection.setDoOutput(true);

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            urlConnection.setDoInput(true);

            // Post 请求不能使用缓存
            urlConnection.setUseCaches(false);

            // 设置通用的请求属性
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            //3. 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
            urlConnection.connect();

            //4.
            OutputStream out = urlConnection.getOutputStream();
            out.write(null);
            out.flush();
            out.close();

            //5.
            InputStream in = urlConnection.getInputStream();

        } finally {

        }
        return null;
    }
}
