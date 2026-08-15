package com.load_tester.http;

import com.load_tester.model.FailureType;
import com.load_tester.model.RequestResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RequestExecutor {

    private final HttpClient client;

    public RequestExecutor() {

        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public CompletableFuture<RequestResult> execute(HttpRequestConfig requestConfig) {

        HttpRequest.Builder builder =
                HttpRequest.newBuilder()
                        .uri(URI.create(requestConfig.getUrl()))
                                .timeout(Duration.ofSeconds(requestConfig.getTimeoutSeconds()));


        if (requestConfig.getHeaders()!= null) {

            for (Map.Entry<String, String> header : requestConfig.getHeaders().entrySet()) {

                builder.header(
                        header.getKey(),
                        header.getValue()
                );
            }
        }

        HttpRequest request;

        if (requestConfig.getMethod().equalsIgnoreCase("GET")) {

            request = builder
                    .GET()
                    .build();

        } else if (requestConfig.getMethod().equalsIgnoreCase("POST")) {

            String requestBody =
                    requestConfig.getBody() == null ? "" : requestConfig.getBody();

            request = builder
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    requestBody
                            )
                    )
                    .build();

        } else {

            throw new IllegalArgumentException(
                    "Unsupported HTTP method: " + requestConfig.getMethod()
            );
        }

        long start = System.nanoTime();
        return client
                .sendAsync(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                )
                .handle((response, error) -> {

                    long latency =
                            TimeUnit.NANOSECONDS.toMillis(
                                    System.nanoTime() - start
                            );

                    if (error != null) {

                        Throwable cause = error;

                        while (cause.getCause() != null) {
                            cause = cause.getCause();
                        }

                        FailureType failureType;

                        if (cause instanceof java.net.http.HttpTimeoutException) {

                            failureType = FailureType.TIMEOUT;

                        } else if (cause instanceof java.net.UnknownHostException) {

                            failureType = FailureType.DNS_ERROR;

                        } else if (cause instanceof java.net.ConnectException) {

                            failureType = FailureType.CONNECTION_ERROR;

                        } else {

                            failureType = FailureType.UNKNOWN;
                        }

                        return new RequestResult(
                                false,
                                -1,
                                latency,
                                failureType
                        );
                    }
                    int statusCode = response.statusCode();

                    if (statusCode >= 200 && statusCode < 400) {

                        return new RequestResult(
                                true,
                                statusCode,
                                latency,
                                FailureType.NONE
                        );
                    }

                    if (statusCode >= 400 && statusCode < 500) {

                        return new RequestResult(
                                false,
                                statusCode,
                                latency,
                                FailureType.CLIENT_ERROR
                        );
                    }

                    if (statusCode >= 500) {

                        return new RequestResult(
                                false,
                                statusCode,
                                latency,
                                FailureType.SERVER_ERROR
                        );
                    }

                    return new RequestResult(
                            false,
                            statusCode,
                            latency,
                            FailureType.UNKNOWN
                    );
                });
    }
}