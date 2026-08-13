package com.load_tester.metrics;

public class MetricsSnapshot {
    private final int activeUsers;
    private final long timestamp;
    private final int inFlightRequests;
    private final int completedRequests;

    public MetricsSnapshot(long timestamp, int activeUsers, int inFlightRequests, int completedRequests){
        this.timestamp=timestamp;
        this.activeUsers=activeUsers;
        this.inFlightRequests=inFlightRequests;
        this.completedRequests=completedRequests;
    }

    public long getTimeStamp(){
        return timestamp;
    }

    public int getActiveUsers(){
        return activeUsers;
    }

    public int getInFlightRequests(){
        return inFlightRequests;
    }

    public int getCompletedRequests(){
        return completedRequests;
    }
}
