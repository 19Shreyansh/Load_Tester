package com.load_tester.metrics;

import java.util.ArrayList;
import java.util.List;

public class TimeSeriesCalculator {

    public List<TimeSeriesMetrics> calculate(
            List<MetricsSnapshot> snapshots) {

        List<TimeSeriesMetrics> results =
                new ArrayList<>();

        if (snapshots.size() < 2) {
            return results;
        }

        for (int i = 1; i < snapshots.size(); i++) {

            MetricsSnapshot previous =
                    snapshots.get(i - 1);

            MetricsSnapshot current =
                    snapshots.get(i);

            long completedDifference =
                    current.getCompletedRequests()
                            - previous.getCompletedRequests();

            long timeDifferenceNanos =
                    current.getTimeStamp()
                            - previous.getTimeStamp();

            double timeDifferenceSeconds =
                    timeDifferenceNanos / 1_000_000_000.0;

            double rps =
                    completedDifference
                            / timeDifferenceSeconds;

            TimeSeriesMetrics metrics =
                    new TimeSeriesMetrics(
                            current.getTimeStamp(),
                            current.getActiveUsers(),
                            current.getInFlightRequests(),
                            current.getCompletedRequests(),
                            rps
                    );

            results.add(metrics);
        }

        return results;
    }
}