package com.load_tester.http;

import java.util.Map;

public class HttpRequestConfig {

    private final String url;
    private final String method;
    private final String body;
    private final Map<String, String> headers;
    private final int timeoutSeconds;

    public HttpRequestConfig(
            String url,
            String method,
            String body,
            Map<String, String> headers,
            int timeoutSeconds) {

        this.url = url;
        this.method = method;
        this.body = body;
        this.headers = headers;
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}