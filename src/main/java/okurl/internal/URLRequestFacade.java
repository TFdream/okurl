package okurl.internal;

import okurl.Request;
import okurl.Response;
import okurl.ResponseBody;
import okurl.util.Util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Ricky Fung
 */
public abstract class URLRequestFacade {

    public static Response newCall(Request request, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(request.getUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        boolean requiresRequestBody = false;
        if(Util.requiresRequestBody(request.getMethod())) {
            requiresRequestBody = true;
            httpURLConnection.setDoOutput(true);
        }

        httpURLConnection.setDoInput(true);
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.setReadTimeout(readTimeout);

        if(request.isCache()) {
            httpURLConnection.setUseCaches(true);
        }

        //add request Header
        for(String headerName : request.getHeaders().names()) {
            httpURLConnection.setRequestProperty(headerName, request.getHeaders().get(headerName));
        }

        // set request method
        httpURLConnection.setRequestMethod(request.getMethod().name());

        // connect server
        httpURLConnection.connect();

        // write request entity
        if(requiresRequestBody) {
            BufferedSink sink = new BufferedSink(httpURLConnection.getOutputStream());
            request.getBody().writeTo(sink);
            sink.close();
        }

        // parse response
        ResponseBody body = new ResponseBody.Builder()
                .source(new BufferedSource(httpURLConnection.getInputStream()))
                .contentLength(httpURLConnection.getContentLength())
                .contentType(httpURLConnection.getContentType())
                .build();

        Response.Builder builder = new Response.Builder();
        builder.code(httpURLConnection.getResponseCode()).message("OK");
        builder.body(body);
        builder.request(request);

        // parse response Header
        Map<String,List<String>> respHeaders =  httpURLConnection.getHeaderFields();
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
