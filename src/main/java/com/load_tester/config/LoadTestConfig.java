package com.load_tester.config;

import com.load_tester.config.workload.WorkloadConfig;

public class LoadTestConfig {
    private final LoadType loadType;
    private final String url;
    private final WorkloadConfig workloadConfig;

    public LoadTestConfig(String url, LoadType loadType, WorkloadConfig workloadConfig) {
        this.url = url;
        this.loadType=loadType;
        this.workloadConfig=workloadConfig;
    }

    public String getUrl(){
        return url;
    }

    public LoadType getLoadType(){
        return loadType;
    }

    public WorkloadConfig getWorkloadConfig(){
        return workloadConfig;
    }

}
