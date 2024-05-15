package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConsumer {
    private final String baseUrl;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public ApiConsumer(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getData(String endpoint) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .build();
        try {
            return httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
