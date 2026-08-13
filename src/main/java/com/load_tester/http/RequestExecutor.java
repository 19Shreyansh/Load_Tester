package com.load_tester.http;

import com.load_tester.model.FailureType;
import com.load_tester.model.RequestResult;

import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RequestExecutor {
    private final HttpClient client;

    public RequestExecutor() {
        client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).version(HttpClient.Version.HTTP_1_1).build();
    }

    public CompletableFuture<RequestResult> execute (String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        long start = System.nanoTime();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .handle((response, error) -> {
                    long latency = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
                    if (error != null) {
                        return createFailureResult(latency, error);
                    }
                    return createResponseResult(response.statusCode(), latency);
                });
    }

    private RequestResult createFailureResult(
            long latency,
            Throwable error) {

        Throwable cause = error;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        FailureType failureType;

        if (cause instanceof HttpTimeoutException) {

            failureType = FailureType.TIMEOUT;

        } else if (cause instanceof UnknownHostException) {

            failureType = FailureType.DNS_ERROR;

        } else if (cause instanceof ConnectException) {

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

    private RequestResult createResponseResult(
            int statusCode,
            long latency) {

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
    }
}