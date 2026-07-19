package com.load_tester.http;

import com.load_tester.model.RequestResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RequestExecutor {
    private final HttpClient client;

    public RequestExecutor() {
        client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).version(HttpClient.Version.HTTP_1_1).build();
    }

    public CompletableFuture<RequestResult> execute (String url){
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        long start = System.nanoTime();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .handle((response, error) -> {
                    long latency = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start);
                    if (error != null) {
                        return new RequestResult(
                                false,
                                -1,
                                latency
                        );
                    }
                    return new RequestResult(
                            response.statusCode() < 500,
                            response.statusCode(),
                            latency
                    );
                }
                );
    }
}
