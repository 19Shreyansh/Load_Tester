package com.load_tester.config;

import com.load_tester.config.workload.WorkloadConfig;
import com.load_tester.http.HttpRequestConfig;

import java.util.Map;

    public class LoadTestConfig {

        private final LoadType loadType;
        private final HttpRequestConfig requestConfig;
        private final WorkloadConfig workloadConfig;

        public LoadTestConfig(
                HttpRequestConfig requestConfig,
                LoadType loadType,
                WorkloadConfig workloadConfig
                ) {

            this.requestConfig=requestConfig;
            this.loadType = loadType;
            this.workloadConfig = workloadConfig;
        }

        public HttpRequestConfig getRequestConfig(){
            return requestConfig;
        }

        public LoadType getLoadType() {
            return loadType;
        }

        public WorkloadConfig getWorkloadConfig() {
            return workloadConfig;
        }

    }