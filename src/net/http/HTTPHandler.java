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
import enums.DeductionToken;
import enums.GamePhase;
import error.NotEnoughActionsException;
import error.ServerSideException;
import net.ClientHandler;
import net.util.BroadcastPackage;
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

        endpoints.put("GET", new HashMap<String, Consumer<HttpExchange>>() {
            {
                put("/http/currentPlayer", (HttpExchange) -> {
                    HashMap<String, String> currentPlayer = new HashMap<>() {
                        {
                            put("id", String.valueOf(game.getCurrentPlayer().id));
                            put("name", game.getCurrentPlayer().name);
                            put("avatar", game.getCurrentPlayer().avatar.toString());
                            put("left-actions", String.valueOf(game.getCurrentPlayer().leftActions));
                            put("gold", String.valueOf(game.getCurrentPlayer().inventory.getGold()));
                            put("sickness", String.valueOf(game.getCurrentPlayer().getSickness()));
                            put("reputation", String.valueOf(game.getCurrentPlayer().getReputation()));
                        }
                    };

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
                        if (idParameter == null)
                            sendResponse(exchange, 400, "Cannot parse the passed parameter.");
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
                    List<ArtifactCard> cards = game.getCurrentPlayer().inventory.getArtifactCards();
                    String responseBody = JON.build(cards.stream().map(c -> c.getName()).toList());

                    try {
                        sendResponse(exchange, 200, responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                put("/http/inventory/ingredient", (HttpExchange exchange) -> {
                    List<IngredientCard> cards = game.getCurrentPlayer().inventory.getIngredientCards();
                    String responseBody = JON.build(cards.stream().map(c -> c.getName()).toList());

                    try {
                        sendResponse(exchange, 200, responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/deductionBoard/table", (HttpExchange exchange) -> {
                    int[][] deductionTable = game.getDeductionTable();
                    String responseBody = JON.build(deductionTable);
                    try {
                        sendResponse(exchange, 200, responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                put("/http/deductionBoard/token", (HttpExchange exchange) -> {
                    HashMap<String[], DeductionToken> deductionTokens = game.getDeductionTokens();
                    String responseBody = JON.build(deductionTokens);
                    try {
                        sendResponse(exchange, 200, responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/getMarkerId/:markedId", (HttpExchange exchange) -> {
                    String handle = HTTPRequestParser.parsePath(exchange.getRequestURI().getPath(), paths);
                    String idParameter = HTTPRequestParser.parseParameter(exchange.getRequestURI().getPath(), handle);
                    try {
                        if (idParameter == null)
                            sendResponse(exchange, 400, "Cannot parse the passed parameter.");
                        int id = Integer.parseInt(idParameter);

                        sendResponse(exchange, 200, String.valueOf(game.getMarkerID(id)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/hasArtifactCard/:name", (HttpExchange exchange) -> {
                    String handle = HTTPRequestParser.parsePath(exchange.getRequestURI().getPath(), paths);
                    String name = HTTPRequestParser.parseParameter(exchange.getRequestURI().getPath(), handle);
                    try {
                        if (name == null)
                            sendResponse(exchange, 400, "Cannot parse the passed parameter.");

                        sendResponse(exchange, 200, String.valueOf(game.hasArtifactCard(name)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        /** POST Methods */

        endpoints.put("POST", new HashMap<String, Consumer<HttpExchange>>() {
            {
                put("/http/createPlayer", (HttpExchange exchange) -> {
                    try {
                        String body = getRequestString(exchange);
                        Map<String, String> parsed = JON.parseMap(body);

                        Avatar avatar = Avatar.Serene;

                        for (Avatar a : Avatar.values())
                            if (a.toString().equals(parsed.get("avatar"))) {
                                avatar = a;
                                break;
                            }

                        int result = game.createUser(Integer.parseInt(parsed.get("id")), parsed.get("name"), avatar);

                        if (result == 0) {
                            sendResponse(exchange, 200,
                                    String.format("Created a user %s with name %s and avatar %s. Result: %d",
                                            parsed.get("id"), parsed.get("name"), parsed.get("avatar"), result));
                            ClientHandler.broadcast(new BroadcastPackage(BroadcastAction.PLAYER_CREATED));
                        } else
                            sendResponse(exchange, 409, String.valueOf(result));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        /** PUT Methods */

        endpoints.put("PUT", new HashMap<String, Consumer<HttpExchange>>() {
            {
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
                            sendResponse(exchange, 200, "Foraged ingredient for client #"
                                    + String.valueOf(game.getCurrentPlayer().id) + ".");
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
                            sendResponse(exchange, 200, "Drawed a mystery card for client #"
                                    + String.valueOf(game.getCurrentPlayer().id) + ".");
                        } else
                            sendResponse(exchange, 409, String.valueOf(result));

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
                            sendResponse(exchange, 200, "Drawed an artifact card for client #"
                                    + String.valueOf(game.getCurrentPlayer().id) + ".");
                        } else
                            sendResponse(exchange, 409, String.valueOf(result));

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

                        sendResponse(exchange, 200, "Transmuted an ingredient card for client #"
                                + String.valueOf(game.getCurrentPlayer().id) + ".");
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

                        sendResponse(exchange, 200, "Discarded an artifact card for client #"
                                + String.valueOf(game.getCurrentPlayer().id) + ".");
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
                put("/http/toggleDeductionTable", (HttpExchange exchange) -> {

                    try {
                        Map<String, String> arguments = JON.parseMap(getRequestString(exchange));
                        game.toggleDeductionTable(arguments.get("ingredient-name"),
                                Integer.parseInt(arguments.get("table-index")));

                        sendResponse(exchange, 200, "Toggled deduction table for client #"
                                + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/setCard", (HttpExchange exchange) -> {

                    try {
                        String cardId = getRequestString(exchange);
                        game.setCard(Integer.parseInt(cardId));

                        sendResponse(exchange, 200,
                                "Set card for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/setMarker", (HttpExchange exchange) -> {

                    try {
                        String markerId = getRequestString(exchange);
                        game.setMarker(Integer.parseInt(markerId));

                        sendResponse(exchange, 200,
                                "Set marker for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/publishTheory", (HttpExchange exchange) -> {

                    try {
                        game.publishTheory();

                        sendResponse(exchange, 200,
                                "Published theory for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NotEnoughActionsException e) {

                        e.printStackTrace();
                    }
                });
                put("/http/debunkTheory", (HttpExchange exchange) -> {

                    try {
                        game.debunkTheory();

                        sendResponse(exchange, 200,
                                "Debunked theory for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NotEnoughActionsException e) {

                        e.printStackTrace();
                    }
                });
                put("/http/activateArtifact", (HttpExchange exchange) -> {

                    try {
                        String name = getRequestString(exchange);
                        game.activateArtifact(name);

                        sendResponse(exchange, 200,
                                "Activated artifact for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/removeArtifactCardAfterUsing", (HttpExchange exchange) -> {

                    try {
                        String name = getRequestString(exchange);
                        game.removeArtifactCardAfterUsing(name);

                        sendResponse(exchange, 200,
                                "Removed artifact for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                put("/http/paralyseEveryone", (HttpExchange exchange) -> {

                    try {
                        game.paralyseEveryone();

                        sendResponse(exchange, 200,
                                "Paralysed everyone for client #" + String.valueOf(game.getCurrentPlayer().id) + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
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
        else
            endpoints.get(method).get(handle).accept(exchange);
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
