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

    public void run(){
        while(System.currentTimeMillis()<endTime)
        {
            executor.execute(url).thenAccept(metrics::record).join();
        }
    }
}
