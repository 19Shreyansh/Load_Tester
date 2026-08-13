package com.load_tester.model;

public class RequestResult {
    private final boolean success;
    private final int statusCode;
    private final long latencyMillis;
    private final FailureType failureType;

    public RequestResult(boolean success, int statusCode, long latencyMillis, FailureType failureType)
    {
        this.success=success;
        this.statusCode=statusCode;
        this.latencyMillis=latencyMillis;
        this.failureType=failureType;
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

    public FailureType getFailureType(){
        return failureType;
    }
}
