package com.load_tester.engine.user;

import com.load_tester.http.HttpRequestConfig;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;

import java.util.Map;

public class VirtualUser implements Runnable{
    private final RequestExecutor executor;
    private final MetricsCollector metrics;
    private final HttpRequestConfig requestConfig;
    private final long endTime;

    public VirtualUser(RequestExecutor executor, MetricsCollector metrics,
                       HttpRequestConfig requestConfig, long endTime){
        this.executor=executor;
        this.metrics=metrics;
        this.requestConfig=requestConfig;
        this.endTime=endTime;
    }

    @Override
    public void run() {
        metrics.userStarted();
        try{
            while(System.currentTimeMillis()<endTime) {
                metrics.requestStarted();
                try {
                    executor.execute(requestConfig).thenAccept(result -> metrics.record(result)).join();
                } finally {
                    metrics.requestFinished();
                }
            }
        }finally {
            metrics.userFinished();
        }
    }
}
