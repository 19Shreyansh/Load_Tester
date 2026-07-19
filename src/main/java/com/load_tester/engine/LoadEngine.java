package com.load_tester.engine;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;
import com.load_tester.model.RequestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LoadEngine
{
    private final RequestExecutor executor =
            new RequestExecutor();
    private final MetricsCollector metrics=new MetricsCollector();
    public void run(LoadTestConfig config) {
        List<CompletableFuture<Void>> futures =
                new ArrayList<>();
        for (int i = 0; i < config.getTotalRequests(); i++){
            CompletableFuture<Void> future=executor.execute(config.getUrl()).thenAccept(requestResult -> {
//                System.out.println("Latency : "+requestResult.getLatencyMillis()+"ms");
                metrics.record(requestResult.getLatencyMillis(),requestResult.isSuccess());
            });
        futures.add(future);
        }
        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .join();
        metrics.printSummary();
    }
}