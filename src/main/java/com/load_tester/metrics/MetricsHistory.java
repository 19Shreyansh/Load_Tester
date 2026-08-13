package com.load_tester.metrics;

import java.util.ArrayList;
import java.util.List;

public class MetricsHistory {

    private final List<MetricsSnapshot> snapshots =
            new ArrayList<>();

    public synchronized void add(MetricsSnapshot snapshot) {
        snapshots.add(snapshot);
    }

    public synchronized void addFinalSnapshot(
            MetricsCollector metrics) {

        MetricsSnapshot snapshot =
                new MetricsSnapshot(
                        System.nanoTime(),
                        metrics.getActiveUsers(),
                        metrics.getInFlightRequests(),
                        metrics.getCompletedRequests()
                );

        snapshots.add(snapshot);
    }

    public synchronized List<MetricsSnapshot> getSnapshots() {
        return new ArrayList<>(snapshots);
    }
}
