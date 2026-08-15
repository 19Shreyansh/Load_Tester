package com.load_tester.config;

import com.load_tester.config.workload.WorkloadConfig;

import java.util.Map;

    public class LoadTestConfig {

        private final LoadType loadType;
        private final String url;
        private final String method;
        private final String body;
        private final Map<String, String> headers;
        private final WorkloadConfig workloadConfig;

        public LoadTestConfig(
                String url,
                String method,
                LoadType loadType,
                String body,
                Map<String, String> headers,
                WorkloadConfig workloadConfig) {

            this.url = url;
            this.method = method;
            this.loadType = loadType;
            this.body = body;
            this.headers = headers;
            this.workloadConfig = workloadConfig;
        }

        public String getUrl() {
            return url;
        }

        public String getMethod() {
            return method;
        }

        public LoadType getLoadType() {
            return loadType;
        }

        public String getBody() {
            return body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public WorkloadConfig getWorkloadConfig() {
            return workloadConfig;
        }
    }