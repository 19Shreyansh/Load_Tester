package com.load_tester;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.config.LoadType;
import com.load_tester.config.workload.FixedRequestsConfig;
import com.load_tester.config.workload.FixedUsersConfig;
import com.load_tester.config.workload.WorkloadConfig;
import com.load_tester.engine.LoadEngine;
import com.load_tester.http.HttpRequestConfig;

import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
//        LoadTestConfig config =
//                new LoadTestConfig(
//                        new HttpRequestConfig(
//                                "https://jsonplaceholder.typicode.com/posts",
//                                "POST",
//                                body,
//                                headers,
//                                20
//                                ),
//                        LoadType.FIXED_USERS,
//                        new FixedUsersConfig(5,10)
//                );
        LoadTestConfig config =
                new LoadTestConfig(
                        new HttpRequestConfig(
                                "https://jsonplaceholder.typicode.com/posts/1",
                                "GET",
                                null,
                                Map.of(),
                                20
                                ),
                        LoadType.FIXED_USERS,
                        new FixedUsersConfig(30,10)
                );
        new LoadEngine().run(config);
    }
}