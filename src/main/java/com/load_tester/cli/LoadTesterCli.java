package com.load_tester.cli;

import com.load_tester.config.LoadTestConfig;
import com.load_tester.config.LoadType;
import com.load_tester.config.workload.FixedUsersConfig;
import com.load_tester.engine.LoadEngine;
import com.load_tester.http.HttpRequestConfig;

public class LoadTesterCli {

    private static final String VERSION = "1.1.0";

    public static void main(String[] args) {

        if (hasArgument(args, "--help")
                || hasArgument(args, "-h")) {

            printHelp();
            return;
        }

        if (hasArgument(args, "--version")) {

            System.out.println(
                    "Load Tester v" + VERSION
            );

            return;
        }

        try {

            CliArguments arguments =
                    CliParser.parse(args);

            LoadTestConfig config =
                    new LoadTestConfig(
                            new HttpRequestConfig(
                                    arguments.getUrl(),
                                    arguments.getMethod(),
                                    arguments.getBody(),
                                    arguments.getHeaders(),
                                    arguments.getTimeout()
                                    ),
                            LoadType.FIXED_USERS,
                            new FixedUsersConfig(
                                    arguments.getUsers(),
                                    arguments.getDuration()
                                    )
                            );

            System.out.println();
            System.out.println("Load Tester");
            System.out.println("-----------");
            System.out.println(
                    "Target   : " + arguments.getUrl()
            );
            System.out.println(
                    "Method   : " + arguments.getMethod()
            );
            System.out.println(
                    "Users    : " + arguments.getUsers()
            );
            System.out.println(
                    "Duration : " + arguments.getDuration() + "s"
            );
            System.out.println(
                    "Timeout  : " + arguments.getTimeout() + "s"
            );
            System.out.println(
                    "Model    : FIXED_USERS"
            );

            System.out.println();
            System.out.println(
                    "Starting load test..."
            );
            System.out.println();

            new LoadEngine().run(config);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error: " + e.getMessage()
            );

            System.err.println(
                    "Use --help for usage."
            );

            System.exit(1);
        }
    }

    private static boolean hasArgument(
            String[] args,
            String target) {

        for (String arg : args) {

            if (arg.equals(target)) {
                return true;
            }
        }

        return false;
    }

    private static void printHelp() {

        System.out.println();
        System.out.println("Load Tester");
        System.out.println();
        System.out.println("Usage:");
        System.out.println(
                "  loadtester --url <url> --method <GET|POST> --users <users> --duration <seconds>"
        );
        System.out.println();
        System.out.println("Options:");
        System.out.println(
                "  --url <url>             Target URL"
        );
        System.out.println(
                "  --method <GET|POST>     HTTP request method"
        );
        System.out.println(
                "  --users <number>        Number of concurrent users"
        );
        System.out.println(
                "  --duration <seconds>    Test duration"
        );
        System.out.println(
                "  --timeout <seconds>     Request timeout (default: 30)"
        );
        System.out.println(
                "  --body <body>           Request body for POST"
        );
        System.out.println(
                "  --body-file <file>      Read POST body from file"
        );
        System.out.println(
                "  --header <name:value>   HTTP request header (repeatable)"
        );
        System.out.println(
                "  --version               Show version"
        );
        System.out.println(
                "  --help, -h              Show this help message"
        );
        System.out.println();
    }
}