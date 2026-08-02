package com.load_tester.engine.model;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FixedRequestsModel implements LoadModel{
    private final LoadTestConfig config;
    private final RequestExecutor executor;
    private final MetricsCollector metrics;

    public FixedRequestsModel(LoadTestConfig config, RequestExecutor executor, MetricsCollector metrics){
        this.config=config;
        this.executor=executor;
        this.metrics=metrics;
    }

    @Override
    public void execute() {
        List<CompletableFuture<Void>> futures =
                new ArrayList<>();
        for (int i = 0; i < config.getTotalRequests(); i++){
            CompletableFuture<Void> future=executor.execute(config.getUrl()).thenAccept(requestResult -> {
                metrics.record(requestResult);
            });
            futures.add(future);
        }
        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .join();

    }


    }


