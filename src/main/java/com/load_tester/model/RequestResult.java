package com.load_tester.model;

public class RequestResult {
    private final boolean success;
    private final int statusCode;
    private final long latencyMillis;

    public RequestResult(boolean success, int statusCode, long latencyMillis)
    {
        this.success=success;
        this.statusCode=statusCode;
        this.latencyMillis=latencyMillis;
    }

    public boolean isSuccess(){
        return success;
    }

    public int getStatusCode(){
        return statusCode;
    }

    public long getLatencyMillis(){
        return latencyMillis;
    }
}
