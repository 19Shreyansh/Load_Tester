package com.load_tester.engine;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.engine.model.LoadModel;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;
import com.load_tester.metrics.MetricsPrinter;
import java.util.concurrent.TimeUnit;

public class LoadEngine
{
    private final MetricsPrinter printer=new MetricsPrinter();
    public void run(LoadTestConfig config) {
        MetricsCollector metrics= new MetricsCollector();
        RequestExecutor executor = new RequestExecutor();
        LoadModel model=LoadModelFactory.create(config,executor,metrics);
        long start=System.nanoTime();
        model.execute();
        long duration=System.nanoTime()-start;
        long durationMillis= TimeUnit.NANOSECONDS.toMillis(duration);
        printer.print(metrics,durationMillis);
    }
}