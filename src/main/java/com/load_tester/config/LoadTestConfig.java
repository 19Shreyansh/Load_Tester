package com.load_tester.config;

import com.load_tester.config.workload.WorkloadConfig;

public class LoadTestConfig {
    private final LoadType loadType;
    private final String url;
    private final String method;
    private final WorkloadConfig workloadConfig;

    public LoadTestConfig(String url, String method, LoadType loadType, WorkloadConfig workloadConfig) {
        this.url = url;
        this.method = method;
        this.loadType=loadType;
        this.workloadConfig=workloadConfig;
    }

    public String getUrl(){
        return url;
    }

    public String getMethod(){
        return method;
    }

    public LoadType getLoadType(){
        return loadType;
    }

    public WorkloadConfig getWorkloadConfig(){
        return workloadConfig;
    }

}
