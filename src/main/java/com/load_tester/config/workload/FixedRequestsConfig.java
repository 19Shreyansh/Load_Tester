package com.load_tester.config.workload;

public class FixedRequestsConfig implements WorkloadConfig{
    private final int totalRequests;
     public FixedRequestsConfig(int totalRequests){
         this.totalRequests=totalRequests;
     }

     public int getTotalRequests(){
         return totalRequests;
     }
}
