package com.load_tester.cli;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CliParser {

    public static CliArguments parse(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "No arguments provided. Use --help for usage."
            );
        }

        Map<String, String> values = new HashMap<>();

        for (int i = 0; i < args.length; i++) {

            String argument = args[i];

            if (!argument.startsWith("--")) {
                throw new IllegalArgumentException(
                        "Unexpected argument: " + argument
                );
            }

            if (i + 1 >= args.length) {
                throw new IllegalArgumentException(
                        "Missing value for " + argument
                );
            }

            values.put(argument, args[++i]);
        }

        String url = values.get("--url");
        String usersValue = values.get("--users");
        String durationValue = values.get("--duration");

        if (url == null) {
            throw new IllegalArgumentException(
                    "Missing required argument: --url"
            );
        }

        if (usersValue == null) {
            throw new IllegalArgumentException(
                    "Missing required argument: --users"
            );
        }

        if (durationValue == null) {
            throw new IllegalArgumentException(
                    "Missing required argument: --duration"
            );
        }

        validateUrl(url);

        int users = parsePositiveInteger(
                usersValue,
                "--users"
        );

        int duration = parsePositiveInteger(
                durationValue,
                "--duration"
        );

        return new CliArguments(url, users, duration);
    }

    private static void validateUrl(String url) {

        try {
            URI uri = URI.create(url);

            if (uri.getScheme() == null ||
                    (!uri.getScheme().equalsIgnoreCase("http")
                            && !uri.getScheme().equalsIgnoreCase("https"))) {

                throw new IllegalArgumentException(
                        "URL must use http or https."
                );
            }

            if (uri.getHost() == null) {
                throw new IllegalArgumentException(
                        "Invalid URL: " + url
                );
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid URL: " + url
            );
        }
    }

    private static int parsePositiveInteger(
            String value,
            String argument) {

        try {

            int number = Integer.parseInt(value);

            if (number <= 0) {
                throw new IllegalArgumentException(
                        argument + " must be greater than 0."
                );
            }

            return number;

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    argument + " must be a valid number."
            );
        }
    }
}