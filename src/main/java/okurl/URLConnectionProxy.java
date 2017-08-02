package okurl;

import okurl.util.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class URLConnectionProxy {

    public static Response newCall(Request request, int connectTimeout, int readTimeout) throws IOException {
        //1.
        URL url = new URL(request.getUrl());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        //2. 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
        boolean requiresRequestBody = false;
        if(Util.requiresRequestBody(request.getMethod())) {
            requiresRequestBody = true;
            urlConnection.setDoOutput(true);
        }

        // 设置是否从httpUrlConnection读入，默认情况下是true;
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setReadTimeout(readTimeout);

        // Post 请求不能使用缓存
        if(request.isCache()) {
            urlConnection.setUseCaches(true);
        }

        // 设置通用的请求属性
        Set<String> headerNames = request.getHeaders().names();
        for(String headerName : headerNames) {
            urlConnection.setRequestProperty(headerName, request.getHeaders().get(headerName));
        }

        urlConnection.setRequestMethod(request.getMethod().name());

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
        ResponseBody body = new ResponseBody.Builder()
                .stream(urlConnection.getInputStream())
                .contentLength(urlConnection.getContentLength())
                .contentType(urlConnection.getContentType())
                .build();

        Response.Builder builder = new Response.Builder();
        builder.code(urlConnection.getResponseCode()).message("OK");
        builder.body(body);
        builder.request(request);

        //响应头
        Map<String,List<String>> respHeaders =  urlConnection.getHeaderFields();
        if(respHeaders!=null) {
            for(Map.Entry<String,List<String>> me : respHeaders.entrySet()) {
                if(me.getValue()!=null) {
                    for(String value : me.getValue()) {
                        builder.addHeader(me.getKey(), value);
                    }
                }
            }
        }

        return builder.build();
    }
}
