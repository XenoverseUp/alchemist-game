package net.http;

import com.sun.net.httpserver.HttpServer;

import domain.TheAlchemistGame;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPServer {
    private HttpServer server;

    public HTTPServer(TheAlchemistGame game) throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/http", new HTTPHandler(game));
        server.setExecutor(null);
        server.start();
        System.out.println("HttpServer is running on port 8080...");
    }
}
