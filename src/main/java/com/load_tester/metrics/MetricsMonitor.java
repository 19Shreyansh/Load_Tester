package com.load_tester.metrics;

public class MetricsMonitor implements Runnable {

    private final MetricsCollector metrics;
    private final MetricsHistory history;

    public MetricsMonitor(
            MetricsCollector metrics,
            MetricsHistory history) {

        this.metrics = metrics;
        this.history = history;
    }

    @Override
    public void run() {

        try {

            while (!Thread.currentThread().isInterrupted()) {

                MetricsSnapshot snapshot =
                        new MetricsSnapshot(
                                System.nanoTime(),
                                metrics.getActiveUsers(),
                                metrics.getInFlightRequests(),
                                metrics.getCompletedRequests()
                        );

                history.add(snapshot);

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}