package com.load_tester.cli;

import java.util.Map;

public class CliArguments {

    private final String url;
    private final int users;
    private final int duration;
    private final String method;
    private final String body;
    private final Map<String, String> headers;

    public CliArguments(String url, int users, int duration, String method, String body, Map<String, String> headers) {
        this.url = url;
        this.users = users;
        this.duration = duration;
        this.method=method;
        this.body=body;
        this.headers=headers;
    }

    public String getUrl() {
        return url;
    }

    public int getUsers() {
        return users;
    }

    public int getDuration() {
        return duration;
    }

    public String getMethod(){
        return method;
    }

    public String getBody(){
        return body;
    }

    public Map<String, String> getHeaders(){
        return headers;
    }

}