package com.load_tester.engine.user;

import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;

public class VirtualUser implements Runnable{
    private final RequestExecutor executor;
    private final MetricsCollector metrics;
    private final String url;
    private final long endTime;

    public VirtualUser(RequestExecutor executor, MetricsCollector metrics, String url, long endTime){
        this.executor=executor;
        this.metrics=metrics;
        this.url=url;
        this.endTime=endTime;
    }

    @Override
    public void run() {
        metrics.userStarted();
        try{
            while(System.currentTimeMillis()<endTime) {
                metrics.requestStarted();
                try {
                    executor.execute(url).thenAccept(result -> metrics.record(result)).join();
                } finally {
                    metrics.requestFinished();
                }
            }
        }finally {
            metrics.userFinished();
        }
    }
}
