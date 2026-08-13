package com.load_tester.engine.model;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.config.workload.FixedUsersConfig;
import com.load_tester.engine.user.VirtualUser;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FixedUsersModel implements LoadModel {
    private final LoadTestConfig config;
    private final RequestExecutor executor;
    private final MetricsCollector metrics;
    private final MetricsHistory history = new MetricsHistory();

    public FixedUsersModel(
            LoadTestConfig config,
            RequestExecutor executor,
            MetricsCollector metrics) {

        this.config = config;
        this.executor = executor;
        this.metrics = metrics;
    }

    public MetricsHistory getHistory() {
        return history;
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
        MetricsMonitor monitor = new MetricsMonitor(metrics, history);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        waitForUsers(futures);
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        history.addFinalSnapshot(metrics);
        executorService.shutdown();
    }

    private void waitForUsers(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}