package okurl;

import okurl.util.Utils;

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
            URL url = new URL(request.getUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //2. 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            boolean requiresRequestBody = false;
            if(Utils.requiresRequestBody(request.getMethod())) {
                requiresRequestBody = true;
                urlConnection.setDoOutput(true);
            }

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(1000);
            // Post 请求不能使用缓存
            if(request.isCache()) {
                urlConnection.setUseCaches(true);
            }

            // 设置通用的请求属性
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            //3. 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
            urlConnection.connect();

            //4.
            if(requiresRequestBody) {
                OutputStream out = urlConnection.getOutputStream();
                out.write(request.getBody().getBytes());
                out.flush();
                out.close();
            }

            //5.
            InputStream in = urlConnection.getInputStream();

        } finally {

        }
        return null;
    }
}
