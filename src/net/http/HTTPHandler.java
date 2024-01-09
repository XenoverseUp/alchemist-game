package net.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import domain.TheAlchemistGame;
import enums.Avatar;
import enums.BroadcastAction;
import enums.GamePhase;
import net.BroadcastPackage;
import net.ClientHandler;
import net.util.JON;


public class HTTPHandler implements HttpHandler {
    private TheAlchemistGame game;
    private HashMap<String, HashMap<String, Consumer<HttpExchange>>> endpoints = new HashMap<String, HashMap<String, Consumer<HttpExchange>>>();
    private List<String> paths = new ArrayList<>();

    public HTTPHandler(TheAlchemistGame game) {
        this.game = game;
        setupRouter();
        this.endpoints.forEach((_k, v) -> v.forEach((k, _v) -> paths.add(k)));
    }

    /**
     * Allowed methods are: GET, POST and PUT.
     */
    private void setupRouter() {
        endpoints.put("GET", new HashMap<String, Consumer<HttpExchange>>() {{
            put("/http/currentPlayer", (HttpExchange) -> {
                HashMap<String, String> currentPlayer = new HashMap<>() {{
                    put("id", String.valueOf(game.getCurrentUser().id));
                    put("name", game.getCurrentUser().name);
                    put("avatar", game.getCurrentUser().avatar.toString());
                    put("left-actions", String.valueOf(game.getCurrentUser().leftActions));
                    put("gold", String.valueOf(game.getCurrentUser().inventory.getGold()));
                    put("sickness", String.valueOf(game.getCurrentUser().getSickness()));
                    put("reputation", String.valueOf(game.getCurrentUser().getReputation()));
                }};

                try {
                    sendResponse(HttpExchange, 200, JON.build(currentPlayer));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            put("/http/playerAvatar/:id", (HttpExchange exchange) -> {
                String handle = HTTPRequestParser.parsePath(exchange.getRequestURI().getPath(), paths);
                String idParameter = HTTPRequestParser.parseParameter(exchange.getRequestURI().getPath(), handle);

                try {
                    if (idParameter == null) sendResponse(exchange, 400, "Cannot parse the passed parameter.");
                    int id = Integer.parseInt(idParameter);

                    sendResponse(exchange, 200, game.getPlayerAvatar(id).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            put("/http/players", (HttpExchange exchange) -> {
                HashMap<String, String> players = game.getPlayers();

                try {
                    sendResponse(exchange, 200, JON.build(players));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            put("/http/gamePhase", (HttpExchange exchange) -> {
                GamePhase currentPhase = game.getPhase();

                try {
                    sendResponse(exchange, 200, currentPhase.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }});

        endpoints.put("POST", new HashMap<String, Consumer<HttpExchange>>() {{
            put("/http/createPlayer", (HttpExchange exchange) -> {
                try {
                    String body = getRequestString(exchange);
                    Map<String, String> parsed = JON.parse(body);

                    Avatar avatar = Avatar.Serene;

                    for (Avatar a : Avatar.values())
                        if (a.toString().equals(parsed.get("avatar"))){
                            avatar = a;
                            break;
                        }
                    
                    int result = game.createUser(Integer.parseInt(parsed.get("id")), parsed.get("name"), avatar);
                    
                    if (result == 0) {
                        sendResponse(exchange, 200, String.format("Created a user %s with name %s and avatar %s. Result: %d", parsed.get("id"), parsed.get("name"), parsed.get("avatar"), result));
                        ClientHandler.broadcast(new BroadcastPackage(BroadcastAction.PLAYER_CREATED));
                    } else sendResponse(exchange, 409, String.valueOf(result));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }});

        endpoints.put("PUT", new HashMap<String, Consumer<HttpExchange>>() {{
            put("/http/togglePlayer", (HttpExchange exchange) -> {
                game.toggleCurrentUser();
                ClientHandler.broadcast(new BroadcastPackage(BroadcastAction.PLAYER_TOGGLED));
                try {
                    sendResponse(exchange, 200, "It fucking works");
                } catch (IOException e) {
                    try {
                        sendResponse(exchange, 500, "Internal Server Error");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            put("/http/startGame", (HttpExchange exchange) -> {
                game.initializeGame();
                try {
                    sendResponse(exchange, 200, "Game is started by the host.");
                    ClientHandler.broadcast(new BroadcastPackage(BroadcastAction.GAME_STARTED));
                } catch (IOException e) {
                    try {
                        sendResponse(exchange, 500, "Internal Server Error");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            
        }});
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String handle = HTTPRequestParser.parsePath(exchange.getRequestURI().getPath(), this.paths);
        System.out.println(method + ": " + handle); // Logging

        if (!(method.equals("GET") || method.equals("POST") || method.equals("PUT")))
            sendResponse(exchange, 405, "Method Not Allowed");
        else if (!endpoints.get(method).containsKey(handle))
            sendResponse(exchange, 405, "Cannot find the path.");
        else endpoints.get(method).get(handle).accept(exchange);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private String getRequestString(HttpExchange exchange) throws IOException {
        try (java.util.Scanner scanner = new java.util.Scanner(exchange.getRequestBody()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
