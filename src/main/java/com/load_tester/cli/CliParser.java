package com.load_tester.cli;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CliParser {
    private static final int DEFAULT_TIMEOUT = 30;
    public static CliArguments parse(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "No arguments provided. Use --help for usage."
            );
        }

        Map<String, String> values = new HashMap<>();
        Map<String, String> headers = new HashMap<>();

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

            String value=args[++i];
            values.put(argument, value);
            if(argument.equals("--header"))
                parseHeader(value,headers);
            else
                values.put(argument,value);
        }

        String url = values.get("--url");
        String method = values.get("--method");
        String usersValue = values.get("--users");
        String durationValue = values.get("--duration");
        String body = values.get("--body");
        String bodyFile = values.get("--body-file");
        String timeoutValue = values.get("--timeout");

        if (url == null) {
            throw new IllegalArgumentException(
                    "Missing required argument: --url"
            );
        }

        if (method == null) {
            throw new IllegalArgumentException(
                    "Missing required argument: --method"
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

        if(body!=null && bodyFile!=null)
            throw new IllegalArgumentException("Use either --body or --body-file, not both.");

        if (bodyFile != null) {
            try {
                body = java.nio.file.Files.readString(
                        java.nio.file.Path.of(bodyFile)
                );
            } catch (java.io.IOException e) {
                throw new IllegalArgumentException(
                        "Unable to read body file: " + bodyFile
                );
            }
        }
        validateUrl(url);

        method = validateMethod(method);

        validateBody(method,body);

        int users = parsePositiveInteger(
                usersValue,
                "--users"
        );

        int duration = parsePositiveInteger(
                durationValue,
                "--duration"
        );

        int timeout = timeoutValue == null
                ? DEFAULT_TIMEOUT
                : parsePositiveInteger(
                timeoutValue,
                "--timeout"
        );

        return new CliArguments(
                url,
                users,
                duration,
                method,
                body,
                bodyFile,
                headers,
                timeout
        );
    }

    private static void  parseHeader(String value, Map<String, String> headers){
        int separator=value.indexOf(":");
        if(separator<=0)
            throw new IllegalArgumentException("Invalid header format. Use: --header \"Name: Value\"");
        String name=value.substring(0,separator).trim();
        String headerValue=value.substring(separator+1).trim();
        if(name.isEmpty())
            throw new IllegalArgumentException("Header name cannot  be empty");
        if(headerValue.isEmpty())
            throw  new IllegalArgumentException("Header value cannot be empty");
        headers.put(name, headerValue);
    }

    public static void validateBody(String method, String body){
        if(body!=null && !method.equalsIgnoreCase("POST"))
            throw new IllegalArgumentException("--body can only be used with POST");
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

    private static String validateMethod(String method) {

        if (!method.equalsIgnoreCase("GET")
                && !method.equalsIgnoreCase("POST")) {

            throw new IllegalArgumentException(
                    "--method must be GET or POST."
            );
        }

        return method.toUpperCase();
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