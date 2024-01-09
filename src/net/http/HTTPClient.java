package net.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

public class HTTPClient {
    private static HTTPClient instance = null;
    private HttpClient httpClient;

    private HTTPClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public static synchronized HTTPClient getInstance() {
        if (instance == null)
            instance = new HTTPClient();

        return instance;
    }


    public HttpResponse<String> get(String path) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://localhost:8080%s", path)))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpResponse<String> post(String path, String body) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://localhost:8080%s", path)))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString(body))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpResponse<String> put(String path, String body) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://localhost:8080%s", path)))
            .header("Content-Type", "text/plain")
            .PUT(BodyPublishers.ofString(body))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpResponse<String> put(String path) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://localhost:8080%s", path)))
            .PUT(BodyPublishers.noBody())
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String buildBody(Map<String, String> data) {
        StringBuilder body = new StringBuilder("");
        data.forEach((k, v) -> body.append(String.format("%s:%s\n", k, v)));

        return body.toString();
    }
    
}
