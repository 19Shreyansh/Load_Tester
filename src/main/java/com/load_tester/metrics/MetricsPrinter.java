package com.load_tester.metrics;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsPrinter {

    public void print(MetricsCollector metrics,
                      long durationMillis) {
        List<Long> latencies = metrics.getLatencySamples();
        long p50=LatencyCalculator.percentile(latencies,50);
        long p95=LatencyCalculator.percentile(latencies,95);
        long p99=LatencyCalculator.percentile(latencies,99);

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

        System.out.println("P50 Latency        : " + p50 + " ms");
        System.out.println("P95 Latency        : " + p95 + " ms");
        System.out.println("P99 Latency        : " + p99 + " ms");

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
        System.out.println();

        System.out.println("Failures");
        System.out.println("--------");

        System.out.println("Connection Errors : "
                + metrics.getConnectionErrors());

        System.out.println("Timeouts          : "
                + metrics.getTimeouts());

        System.out.println("DNS Errors        : "
                + metrics.getDnsErrors());

        System.out.println("Client Errors     : "
                + metrics.getClientErrors());

        System.out.println("Server Errors     : "
                + metrics.getServerErrors());

        System.out.println("Unknown Errors    : "
                + metrics.getUnknownErrors());
        System.out.println("=================================");
    }
}