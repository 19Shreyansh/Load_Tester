package com.load_tester.metrics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class MetricsCollector {
    private final AtomicInteger completedReq=new AtomicInteger();
    private final AtomicInteger successReq=new AtomicInteger();
    private final AtomicInteger failedReq=new AtomicInteger();
    private final AtomicLong totalLatency=new AtomicLong();

    public void record(long latency,boolean success ){
        completedReq.incrementAndGet();
        totalLatency.addAndGet(latency);
        if(success)
            successReq.incrementAndGet();
        else failedReq.incrementAndGet();
    }

    public void printSummary(){
        long completed= completedReq.get();
        long avgLatency=completed==0?0:totalLatency.get()/completed;
        System.out.println("\n===== SUMMARY =====");

        System.out.println("Completed : " + completed);

        System.out.println("Succeeded : " +
                successReq.get());

        System.out.println("Failed    : " +
                failedReq.get());

        System.out.println("Avg Latency : " +
                avgLatency + " ms");
    }

}
