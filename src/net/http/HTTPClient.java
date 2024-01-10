package net.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;

public class HTTPClient {
    private static HTTPClient instance = null;
    private HttpClient httpClient;
    private String host = "localhost";

    private HTTPClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public static synchronized HTTPClient getInstance() {
        if (instance == null)
            instance = new HTTPClient();

        return instance;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public HttpResponse<String> get(String path) {
        HttpRequest request = HttpRequest.newBuilder()
            .version(Version.HTTP_1_1)
            .uri(URI.create(String.format("http://%s:8080%s", host, path)))
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
            .version(Version.HTTP_1_1)
            .uri(URI.create(String.format("http://%s:8080%s", host, path)))
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
            .version(Version.HTTP_1_1)
            .uri(URI.create(String.format("http://%s:8080%s", host, path)))
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
            .version(Version.HTTP_1_1)
            .uri(URI.create(String.format("http://%s:8080%s", host, path)))
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
}
