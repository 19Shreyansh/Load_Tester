package com.load_tester.metrics;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsPrinter {

    public void print(MetricsCollector metrics, long durationMillis) {
        List<Long> latencies = metrics.getLatencySamples();
        long p50 = LatencyCalculator.percentile(latencies, 50);
        long p95 = LatencyCalculator.percentile(latencies, 95);
        long p99 = LatencyCalculator.percentile(latencies, 99);
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
        System.out.println();
        System.out.println("Latency");
        System.out.println("-------");
        System.out.printf(
                "%-20s : %s%n",
                "P50",
                formatPercentile(p50)
        );
        System.out.printf(
                "%-20s : %s%n",
                "P95",
                formatPercentile(p95)
        );
        System.out.printf(
                "%-20s : %s%n",
                "P99",
                formatPercentile(p99)
        );
        System.out.printf(
                "%-20s : %d ms%n",
                "Average",
                metrics.getAverageLatency()
        );
        System.out.printf(
                "%-20s : %d ms%n",
                "Minimum",
                metrics.getMinLatency()
        );
        System.out.printf(
                "%-20s : %d ms%n",
                "Maximum",
                metrics.getMaxLatency()
        );
        System.out.println();
        printStatusCodes(metrics);
        printFailures(metrics);
        System.out.println("=================================");
    }
    private String formatPercentile(long value){
        if(value<0) return "N/A";
        return value+"ms";
    }

    private void printStatusCodes(MetricsCollector metrics) {
        System.out.println("Status Codes");
        System.out.println("------------");
        for (Map.Entry<Integer, AtomicInteger> entry
                : metrics.getStatusCodeCounts().entrySet()) {
            int statusCode = entry.getKey();
            if (statusCode < 0) {
                continue;
            }
            System.out.printf(
                    "%d : %d%n",
                    statusCode,
                    entry.getValue().get()
            );
        }
        System.out.println();
    }
    private void printFailures(MetricsCollector metrics) {
        int totalFailures = metrics.getFailedRequests();
        if (totalFailures == 0) {
            return;
        }
        System.out.println("Failures");
        System.out.println("--------");
        System.out.println("Connection Errors : "+ metrics.getConnectionErrors());
        System.out.println("Timeouts          : "+ metrics.getTimeouts());
        System.out.println("DNS Errors        : "+ metrics.getDnsErrors());
        System.out.println("Client Errors     : "+ metrics.getClientErrors());
        System.out.println("Server Errors     : "+ metrics.getServerErrors());
        System.out.println("Unknown Errors    : "+ metrics.getUnknownErrors());
        System.out.println();
    }
}