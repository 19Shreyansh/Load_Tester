package com.load_tester.metrics;

import com.load_tester.model.FailureType;
import com.load_tester.model.RequestResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private final AtomicInteger connectionErrors = new AtomicInteger();
    private final AtomicInteger timeouts = new AtomicInteger();
    private final AtomicInteger dnsErrors = new AtomicInteger();
    private final AtomicInteger clientErrors = new AtomicInteger();
    private final AtomicInteger serverErrors = new AtomicInteger();
    private final AtomicInteger unknownErrors = new AtomicInteger();

    private final AtomicLong minLatency = new AtomicLong(Long.MAX_VALUE);
    private final AtomicLong maxLatency = new AtomicLong(Long.MIN_VALUE);
    private final ConcurrentHashMap<Integer, AtomicInteger> statusCodeCounts = new ConcurrentHashMap<>();
    private final List<Long> latencySamples= Collections.synchronizedList(new ArrayList<>());

    public List<Long> getLatencySamples(){
        synchronized (latencySamples){
            return new ArrayList<>(latencySamples);
        }
    }

    private void recordFailure(FailureType failureType) {
        switch (failureType) {
            case CONNECTION_ERROR -> connectionErrors.incrementAndGet();
            case TIMEOUT -> timeouts.incrementAndGet();
            case DNS_ERROR -> dnsErrors.incrementAndGet();
            case CLIENT_ERROR -> clientErrors.incrementAndGet();
            case SERVER_ERROR -> serverErrors.incrementAndGet();
            case UNKNOWN -> unknownErrors.incrementAndGet();
            case NONE -> {
                // Nothing to record
            }
        }
    }

    public void record(RequestResult result) {
        completedRequests.incrementAndGet();
        totalLatency.addAndGet(result.getLatencyMillis());
        minLatency.accumulateAndGet(
                result.getLatencyMillis(),
                Math::min
        );
        maxLatency.accumulateAndGet(
                result.getLatencyMillis(),
                Math::max
        );
        if (result.isSuccess()) {
            successfulRequests.incrementAndGet();
            latencySamples.add(result.getLatencyMillis());
        } else {
            failedRequests.incrementAndGet();
            recordFailure(result.getFailureType());
        }
        statusCodeCounts.computeIfAbsent(result.getStatusCode(),
                key -> new AtomicInteger()).incrementAndGet();
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
    public int getConnectionErrors() {
        return connectionErrors.get();
    }
    public int getTimeouts() {
        return timeouts.get();
    }
    public int getDnsErrors() {
        return dnsErrors.get();
    }
    public int getClientErrors() {
        return clientErrors.get();
    }
    public int getServerErrors() {
        return serverErrors.get();
    }
    public int getUnknownErrors() {
        return unknownErrors.get();
    }
}