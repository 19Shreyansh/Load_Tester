package com.load_tester.metrics;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsPrinter {

    public void print(MetricsCollector metrics,
                      long durationMillis) {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        LOAD TEST SUMMARY");
        System.out.println("=================================");

        System.out.printf(
                "%-20s : %d%n",
                "Completed",
                metrics.getCompletedRequests()
        );

        System.out.printf(
                "%-20s : %d%n",
                "Succeeded",
                metrics.getSuccessfulRequests()
        );

        System.out.printf(
                "%-20s : %d%n",
                "Failed",
                metrics.getFailedRequests()
        );

        System.out.printf(
                "%-20s : %.2f %% %n",
                "Success Rate",
                metrics.getSuccessRate()
        );

        System.out.println();

        System.out.printf(
                "%-20s : %.2f req/sec%n",
                "Throughput",
                metrics.getThroughput(durationMillis)
        );

        System.out.printf(
                "%-20s : %d ms%n",
                "Average Latency",
                metrics.getAverageLatency()
        );

        System.out.printf(
                "%-20s : %d ms%n",
                "Minimum Latency",
                metrics.getMinLatency()
        );

        System.out.printf(
                "%-20s : %d ms%n",
                "Maximum Latency",
                metrics.getMaxLatency()
        );

        System.out.println();
        System.out.println("Status Codes");
        System.out.println("------------");

        for (Map.Entry<Integer, AtomicInteger> entry
                : metrics.getStatusCodeCounts().entrySet()) {

            System.out.printf(
                    "%d : %d%n",
                    entry.getKey(),
                    entry.getValue().get()
            );
        }

        System.out.println("=================================");
    }
}