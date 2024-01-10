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

import domain.ArtifactCard;
import domain.IngredientCard;
import domain.TheAlchemistGame;
import enums.Avatar;
import enums.BroadcastAction;
import enums.GamePhase;
import error.NotEnoughActionsException;
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


    private void setupRouter() {

        /** GET Methods */

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
            put("/http/game/players", (HttpExchange exchange) -> {
                HashMap<String, String> players = game.getPlayers();

                try {
                    sendResponse(exchange, 200, JON.build(players));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            put("/http/game/phase", (HttpExchange exchange) -> {
                GamePhase currentPhase = game.getPhase();

                try {
                    sendResponse(exchange, 200, currentPhase.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            put("/http/inventory/artifact", (HttpExchange exchange) -> {
                List<ArtifactCard> cards = game.getCurrentUser().inventory.getArtifactCards();
                String responseBody = JON.build(cards.stream().map(c -> c.getName()).toList());

                try {
                    sendResponse(exchange, 200, responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
               
            });
            put("/http/inventory/ingredient", (HttpExchange exchange) -> {
                List<IngredientCard> cards = game.getCurrentUser().inventory.getIngredientCards();
                String responseBody = JON.build(cards.stream().map(c -> c.getName()).toList());

                try {
                    sendResponse(exchange, 200, responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }});


        /** POST Methods */

        endpoints.put("POST", new HashMap<String, Consumer<HttpExchange>>() {{
            put("/http/createPlayer", (HttpExchange exchange) -> {
                try {
                    String body = getRequestString(exchange);
                    Map<String, String> parsed = JON.parseMap(body);

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


        /** PUT Methods */

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
            put("/http/forageIngredient", (HttpExchange exchange) -> {
                try {
                    game.forageIngredient();
                    try {
                        sendResponse(exchange, 200, "Foraged ingredient for client #" + String.valueOf(game.getCurrentUser().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (NotEnoughActionsException e) {
                    try {
                        sendResponse(exchange, 400, "Not enough actions.");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            put("/http/drawMysteryCard", (HttpExchange exchange) -> {
                try {
                    int result = game.drawMysteryCard();

                    if (result == 0) {
                        sendResponse(exchange, 200, "Drawed a mystery card for client #" + String.valueOf(game.getCurrentUser().id) + ".");
                    } else sendResponse(exchange, 409, String.valueOf(result));
                    
                    
                } catch (NotEnoughActionsException e) {
                    try {
                        sendResponse(exchange, 400, "Not enough actions.");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            put("/http/buyArtifact", (HttpExchange exchange) -> {
                try {
                    String body = getRequestString(exchange);
                    int result = game.buyArtifact(body);

                    if (result == 0) {
                        sendResponse(exchange, 200, "Drawed an artifact card for client #" + String.valueOf(game.getCurrentUser().id) + ".");
                    } else sendResponse(exchange, 409, String.valueOf(result));
                    
                    
                } catch (NotEnoughActionsException e) {
                    try {
                        sendResponse(exchange, 400, "Not enough actions.");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            put("/http/transmuteIngredient", (HttpExchange exchange) -> {
                try {
                    String ingredient = getRequestString(exchange);
                    game.transmuteIngredient(ingredient);

                    sendResponse(exchange, 200, "Transmuted an ingredient card for client #" + String.valueOf(game.getCurrentUser().id) + ".");
                } catch (NotEnoughActionsException e) {
                    try {
                        sendResponse(exchange, 400, "Not enough actions.");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            put("/http/discardArtifact", (HttpExchange exchange) -> {
                try {
                    String artifact = getRequestString(exchange);
                    game.discardArtifact(artifact);

                    sendResponse(exchange, 200, "Discarded an artifact card for client #" + String.valueOf(game.getCurrentUser().id) + ".");
                } catch (NotEnoughActionsException e) {
                    try {
                        sendResponse(exchange, 400, "Not enough actions.");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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