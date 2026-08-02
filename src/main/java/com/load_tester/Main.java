package com.load_tester;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.engine.LoadEngine;

public class Main
{
    public static void main(String[] args)
    {
        LoadTestConfig config =
                new LoadTestConfig(
//                        "https://httpbin.org/get",
                        "https://jsonplaceholder.typicode.com/posts/1",
                        "GET",
                        500
                );
        new LoadEngine().run(config);
    }
}