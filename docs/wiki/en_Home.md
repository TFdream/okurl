# Home

## Dependencies
```
<dependency>
    <groupId>com.mindflow</groupId>
    <artifactId>okurl</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Quick Start

### 1. Synchronous Get
```
    private final OkURLClient client = new OkURLClient();

    public void run() throws IOException {

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
```

### 2. Access Headers
```
    private final OkURLClient client = new OkURLClient();

    public void run() throws IOException {

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
```

### 3. Post form
```
    private final OkURLClient client = new OkURLClient();

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
```

### 4. Post a String

### 5. Post Streaming

### 6. Post a File