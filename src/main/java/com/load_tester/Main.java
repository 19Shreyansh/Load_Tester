package com.load_tester;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.config.LoadType;
import com.load_tester.config.workload.FixedRequestsConfig;
import com.load_tester.config.workload.FixedUsersConfig;
import com.load_tester.config.workload.WorkloadConfig;
import com.load_tester.engine.LoadEngine;

public class Main
{
    public static void main(String[] args)
    {
//        LoadTestConfig config =
//                new LoadTestConfig(
////                        "https://httpbin.org/get",
//                        "https://jsonplaceholder.typicode.com/posts/1",
//                        "GET",
//                        LoadType.FIXED_REQUESTS,
//                        new FixedRequestsConfig(500)
//                );
        LoadTestConfig config =
                new LoadTestConfig(
//                        "https://httpbin.org/get",
                        "https://jsonplaceholder.typicode.com/posts/1",
                        "GET",
                        LoadType.FIXED_USERS,
                        new FixedUsersConfig(30,10)
                );
        new LoadEngine().run(config);
    }
}