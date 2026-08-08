package com.load_tester.engine;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.engine.model.FixedRequestsModel;
import com.load_tester.engine.model.FixedUsersModel;
import com.load_tester.engine.model.LoadModel;
import com.load_tester.http.RequestExecutor;
import com.load_tester.metrics.MetricsCollector;

public class LoadModelFactory {

    public static LoadModel create(
            LoadTestConfig config,
            RequestExecutor executor,
            MetricsCollector metrics) {

        return switch (config.getLoadType()) {

            case FIXED_REQUESTS ->
                    new FixedRequestsModel(
                            config,
                            executor,
                            metrics
                    );

            case FIXED_USERS ->
                    new FixedUsersModel(
                            config,
                            executor,
                            metrics
                    );
        };
    }
}