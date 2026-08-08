package com.load_tester.engine.model;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.config.workload.FixedUsersConfig;
import com.load_tester.engine.user.VirtualUser;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FixedUsersModel implements LoadModel {
    private final LoadTestConfig config;
    private final RequestExecutor executor;
    private final MetricsCollector metrics;

    public FixedUsersModel(
            LoadTestConfig config,
            RequestExecutor executor,
            MetricsCollector metrics) {

        this.config = config;
        this.executor = executor;
        this.metrics = metrics;
    }

    @Override
    public void execute() {
        FixedUsersConfig usersConfig = (FixedUsersConfig) config.getWorkloadConfig();
        int users = usersConfig.getUsers();
        long endTime = System.currentTimeMillis() + usersConfig.getDurationSeconds() * 1000L;
        ExecutorService executorService = Executors.newFixedThreadPool(users);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < users; i++) {
            VirtualUser user = new VirtualUser(executor, metrics, config.getUrl(), endTime);
            Future<?> future = executorService.submit(user);
            futures.add(future);
        }
        Thread monitor = new Thread(() -> {

            try {

                while (!Thread.currentThread().isInterrupted()) {

                    System.out.println(
                            "Active Users = "
                                    + metrics.getActiveUsers()
                                    + " | In-Flight = "
                                    + metrics.getInFlightRequests()
                                    + " | Completed = "
                                    + metrics.getCompletedRequests()
                    );

                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        });

        monitor.start();


// Wait for all users
        for (Future<?> future : futures) {

            try {

                future.get();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

            } catch (ExecutionException e) {

                e.printStackTrace();
            }
        }


// Stop monitoring
        monitor.interrupt();
        try{
            monitor.join();
        }catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }


// Shut down executor
        executorService.shutdown();
    }
}