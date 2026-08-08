package com.load_tester.metrics;

import com.load_tester.model.RequestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsCollector {

    private final AtomicInteger completedRequests = new AtomicInteger();
    private final AtomicInteger successfulRequests = new AtomicInteger();
    private final AtomicInteger failedRequests = new AtomicInteger();
    private final AtomicLong totalLatency = new AtomicLong();
    private final AtomicInteger activeUsers = new AtomicInteger();
    private final AtomicInteger inFlightRequests = new AtomicInteger();

    private final AtomicLong minLatency =
            new AtomicLong(Long.MAX_VALUE);
    private final AtomicLong maxLatency =
            new AtomicLong(Long.MIN_VALUE);
    private final ConcurrentHashMap<Integer, AtomicInteger>
            statusCodeCounts = new ConcurrentHashMap<>();


    public void record(RequestResult result) {

        completedRequests.incrementAndGet();

        totalLatency.addAndGet(
                result.getLatencyMillis()
        );

        if (result.isSuccess()) {
            successfulRequests.incrementAndGet();
        } else {
            failedRequests.incrementAndGet();
        }

        minLatency.accumulateAndGet(
                result.getLatencyMillis(),
                Math::min
        );

        maxLatency.accumulateAndGet(
                result.getLatencyMillis(),
                Math::max
        );

        statusCodeCounts
                .computeIfAbsent(
                        result.getStatusCode(),
                        key -> new AtomicInteger()
                )
                .incrementAndGet();
    }
    public int getCompletedRequests() {
        return completedRequests.get();
    }
    public int getSuccessfulRequests() {
        return successfulRequests.get();
    }
    public int getFailedRequests() {
        return failedRequests.get();
    }
    public long getAverageLatency() {
        if (completedRequests.get() == 0)
            return 0;
        return totalLatency.get() / completedRequests.get();
    }
    public long getMinLatency() {
        return minLatency.get();
    }
    public long getMaxLatency() {
        return maxLatency.get();
    }
    public double getSuccessRate() {
        if (completedRequests.get() == 0)
            return 0;
        return successfulRequests.get() * 100.0
                / completedRequests.get();
    }
    public double getThroughput(long durationMillis) {
        if (durationMillis == 0)
            return 0;
        return completedRequests.get()
                / (durationMillis / 1000.0);
    }
    public Map<Integer, AtomicInteger> getStatusCodeCounts() {
        return statusCodeCounts;
    }

    public void userStarted(){
        activeUsers.incrementAndGet();
    }
    public void userFinished(){
        activeUsers.decrementAndGet();
    }
    public int getActiveUsers(){
        return activeUsers.get();
    }

    public void requestStarted(){
        inFlightRequests.incrementAndGet();
    }
    public void requestFinished(){
        inFlightRequests.decrementAndGet();
    }

    public int getInFlightRequests(){
        return inFlightRequests.get();
    }

}