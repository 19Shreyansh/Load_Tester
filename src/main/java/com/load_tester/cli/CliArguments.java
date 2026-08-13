package com.load_tester.cli;

public class CliArguments {

    private final String url;
    private final int users;
    private final int duration;

    public CliArguments(String url, int users, int duration) {
        this.url = url;
        this.users = users;
        this.duration = duration;
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
}