# OkURL
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Release Version](https://img.shields.io/badge/release-0.1.0-red.svg)](https://github.com/TiFG/okurl/releases) [![Build Status](https://travis-ci.org/TiFG/okurl.svg?branch=master)](https://travis-ci.org/TiFG/okurl)

## Overview
A light-weight HTTP client for Android and Java applications. 

## Requirements
The minimum requirements to run the quick start are:
* JDK 1.7 or above
* A java-based project management software like [Maven](https://maven.apache.org/) or [Gradle](http://gradle.org/)

## Quick Start

### 1. maven dependency
```
<dependency>
    <groupId>com.mindflow</groupId>
	<artifactId>okurl</artifactId>
	<version>0.1.0</version>
</dependency>
```

### 2. http get
```
    private final OkUrlClient client = new OkUrlClient();

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

        System.out.println(response.body().string());
    }
```

### 3. http post
