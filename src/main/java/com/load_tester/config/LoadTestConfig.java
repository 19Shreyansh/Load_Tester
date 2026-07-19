package com.load_tester.config;

public class LoadTestConfig {
    private final String url;
    private final String method;
    private final int totalRequests;

    public LoadTestConfig(String url, String method, int totalRequests) {
        this.url = url;
        this.method = method;
        this.totalRequests = totalRequests;
    }

    public String getUrl(){
        return url;
    }

    public int getTotalRequests(){
        return totalRequests;
    }
}
