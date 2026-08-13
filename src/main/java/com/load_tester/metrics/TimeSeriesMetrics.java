package com.load_tester.metrics;

public class TimeSeriesMetrics {

    private final long timestamp;
    private final int activeUsers;
    private final int inFlightRequests;
    private final int completedRequests;
    private final double rps;

    public TimeSeriesMetrics(
            long timestamp,
            int activeUsers,
            int inFlightRequests,
            int completedRequests,
            double rps) {

        this.timestamp = timestamp;
        this.activeUsers = activeUsers;
        this.inFlightRequests = inFlightRequests;
        this.completedRequests = completedRequests;
        this.rps = rps;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public int getInFlightRequests() {
        return inFlightRequests;
    }

    public int getCompletedRequests() {
        return completedRequests;
    }

    public double getRps() {
        return rps;
    }
}