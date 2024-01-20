package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Avatar;
import enums.BroadcastAction;
import enums.DeductionToken;
import enums.GamePhase;
import enums.Potion;
import error.HostDoesNotExistsException;
import error.NotEnoughActionsException;
import error.ServerSideException;
import error.WrongGameRoundException;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import net.util.BroadcastPackage;
import net.util.Cache;
import net.util.DynamicTypeValue;
import net.util.HTTPClient;
import net.util.JON;

public class Client {
    private int id;
    private Socket socket;
    private ObjectInputStream in;
    private HTTPClient request;
    private ArrayList<IBroadcastListener> broadcastListeners = new ArrayList<IBroadcastListener>();
    private Cache cache = new Cache();

    public Client(int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket("localhost", port);
            this.in = new ObjectInputStream(socket.getInputStream());
            this.request = HTTPClient.getInstance();
            setupCache();
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

    }

    public Client(String host, int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket(host, port);
            this.in = new ObjectInputStream(socket.getInputStream());
            this.request = HTTPClient.getInstance();
            this.request.setHost(host);
            setupCache();
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

    }

    private void setupCache() {
        cache.create("game-phase", this::gamePhaseSupplier);
        cache.create("current-player", this::currentPlayerSupplier);
    }

    /** HTTP Methods */

    public int createUser(int id, String name, Avatar avatar) {
        String body = JON.build(new HashMap<String, String>() {
            {
                put("name", name);
                put("id", String.valueOf(id));
                put("avatar", avatar.toString());
            }
        });

        HttpResponse<String> response = request.post("/http/createPlayer", body);

        if (response.statusCode() == 200)
            return 0;
        else
            return Integer.parseInt((String) response.body());
    }

    public Map<String, String> getPlayerNames() {
        HttpResponse<String> response = request.get("/http/game/players");
        Map<String, String> data = JON.parseMap((String) response.body());

        return data;
    }

    public Map<String, String> getPlayerScores() {
        HttpResponse<String> response = request.get("/http/game/scores");
        Map<String, String> data = JON.parseMap((String) response.body());

        return data;
    }

    public Avatar getAvatar(int id) {
        HttpResponse<String> response = request.get(String.format("/http/playerAvatar/%d", id));

        if (response.statusCode() == 200)
            return Avatar.valueOf((String) response.body());
        return null;
    }

    public void startGame() throws ServerSideException {
        String body = JON.build(new HashMap<String, String>() {
            {
                put("id", String.valueOf(id));
            }
        });

        HttpResponse<String> response = request.put("/http/startGame", body);

        if (response.statusCode() != 200)
            throw new ServerSideException();
    }

    public Map<String, String> getCurrentUser(boolean cached) {
        if (!cached)
            cache.revalidate("current-player");
        String body = cache.get("current-player");

        return JON.parseMap(body);
    }

    public Map<String, String> getCurrentUser() {
        cache.revalidate("current-player");
        String body = cache.get("current-player");

        return JON.parseMap(body);
    }

    public GamePhase getPhase(boolean cached) {
        if (!cached)
            cache.revalidate("game-phase");
        String phase = cache.get("game-phase");

        return GamePhase.valueOf(phase);
    }

    public GamePhase getPhase() {
        cache.revalidate("game-phase");
        String phase = cache.get("game-phase");

        return GamePhase.valueOf(phase);
    }

    public void toggleCurrentUser() throws ServerSideException {
        HttpResponse<String> response = request.put("/http/togglePlayer");
        if (response.statusCode() != 200)
            throw new ServerSideException();
    }

    public void forageIngredient() throws NotEnoughActionsException {
        HttpResponse<String> response = request.put("/http/forageIngredient");

        if (response.statusCode() == 400)
            throw new NotEnoughActionsException();
    }

    public int drawMysteryCard() throws NotEnoughActionsException {
        HttpResponse<String> response = request.put("/http/drawMysteryCard");

        if (response.statusCode() == 400)
            throw new NotEnoughActionsException();
        else if (response.statusCode() == 200)
            return 0;
        else
            return Integer.parseInt((String) response.body());
    }

    public int buyArtifact(String name) throws NotEnoughActionsException {
        HttpResponse<String> response = request.put("/http/buyArtifact", name);

        if (response.statusCode() == 400)
            throw new NotEnoughActionsException();
        else if (response.statusCode() == 200)
            return 0;
        else
            return Integer.parseInt((String) response.body());
    }

    public List<String> getCurrentPlayerArtifacts() {
        HttpResponse<String> response = request.get("/http/inventory/artifact");

        if (response.statusCode() == 200)
            return JON.parseList((String) response.body());

        return null;
    }

    public List<String> getCurrentPlayerIngredients() {
        HttpResponse<String> response = request.get("/http/inventory/ingredient");

        if (response.statusCode() == 200)
            return JON.parseList((String) response.body());

        return null;
    }

    public void transmuteIngredient(String name) throws NotEnoughActionsException {
        HttpResponse<String> response = request.put("/http/transmuteIngredient", name);

        if (response.statusCode() == 400)
            throw new NotEnoughActionsException();
    }

    public void discardArtifact(String name) throws NotEnoughActionsException {
        HttpResponse<String> response = request.put("/http/discardArtifact", name);

        if (response.statusCode() == 400)
            throw new NotEnoughActionsException();
    }

    public int[][] getCurrentPlayerDeduction() {
        HttpResponse<String> response = request.get("/http/deductionBoard/table");

        return JON.parseMatrix((String) response.body());
    }

    public HashMap<String[], DeductionToken> getDeductionTokens() {
        HttpResponse<String> response = request.get("/http/deductionBoard/token");

        return JON.parseMapStringArrayDeductionToken((String) response.body());
    }

    public void toggleDeductionTable(String name, int tableIndex) {
        String body = JON.build(new HashMap<String, String>() {
            {
                put("ingredient-name", name);
                put("table-index", String.valueOf(tableIndex));
            }
        });

        request.put("/http/toggleDeductionTable", body);
    }

    public void setCard(int i) throws ServerSideException {
        HttpResponse<String> response = request.put("/http/setCard", String.valueOf(i));
        if (response.statusCode() != 200)
            throw new ServerSideException();
    }

    public void setMarker(int i) throws ServerSideException {
        HttpResponse<String> response = request.put("/http/setMarker", String.valueOf(i));
        if (response.statusCode() != 200)
            throw new ServerSideException();
    }

    public int publishTheory() throws ServerSideException {
        HttpResponse<String> response = request.get("/http/publishTheory");
        if (response.statusCode() != 200)
            throw new ServerSideException();

        return Integer.parseInt((String) response.body());
    }

    public int debunkTheory() throws ServerSideException {
        HttpResponse<String> response = request.get("/http/debunkTheory");
        if (response.statusCode() != 200)
            throw new ServerSideException();

        return Integer.parseInt((String) response.body());
    }

    public int getMarkerId(int i) throws ServerSideException {
        HttpResponse<String> response = request.get(String.format("/http/getMarkerId/%d", i));
        if (response.statusCode() != 200)
            throw new ServerSideException();

        return Integer.parseInt((String) response.body());
    }

    public void activateArtifact(String name) throws ServerSideException {
        HttpResponse<String> response = request.put("/http/activateArtifact");
        if (response.statusCode() != 200)
            throw new ServerSideException();
    }

    public void removeArtifactCardAfterUsing(String name) throws ServerSideException {
        HttpResponse<String> response = request.put("/http/removeArtifactCardAfterUsing", name);
        if (response.statusCode() != 200)
            throw new ServerSideException();
    }

    public void paralyseEveryone() throws ServerSideException {
        HttpResponse<String> response = request.put("/http/paralyseEveryone");
        if (response.statusCode() != 200)
            throw new ServerSideException();

        request.put("/http/toggleDeductionTable");
    }

    public void finishGame() {
        request.put("/http/finishGame");
    }

    public ArrayList<Integer> calculateWinner() {
        HttpResponse<String> response = request.put("/http/calculateWinner");

        return JON.parseListInt((String) response.body());

    }

    public boolean hasArtifactCard(String name) throws ServerSideException {
        HttpResponse<String> response = request.get(String.format("/http/hasArtifactCard/%s", name));
        if (response.statusCode() != 200)
            throw new ServerSideException();
        return Boolean.parseBoolean((String) response.body());
    }

    public void swapAfterIndex(int first, int second, int third) {
        String body = JON.build(new HashMap<String, String>() {
            {
                put("first", String.valueOf(first));
                put("second", String.valueOf(second));
                put("third", String.valueOf(third));
            }
        });

        request.put("/http/swapAfterIndex", body);
    }

    public List<String> getIngredients() {
        HttpResponse<String> response = request.get("/http/board/ingredients");

        if (response.statusCode() == 200)
            return JON.parseList((String) response.body());

        return null;
    }

    public Potion makeExperiment(
            String ingredientName1,
            String ingredientName2,
            String testOn) throws WrongGameRoundException, NotEnoughActionsException, Exception {
        HashMap<String, String> data = new HashMap<>() {
            {
                put("ingredient1", ingredientName1);
                put("ingredient2", ingredientName2);
                put("testOn", testOn);
            }
        };
        String body = JON.build(data);
        HttpResponse<String> response = request.put("/http/makeExperiment", body);

        if (response.statusCode() == 200) {
            return Potion.valueOf(((String) response.body()));
        } else if (response.statusCode() == 400)
            throw new NotEnoughActionsException();
        else if (response.statusCode() == 401)
            throw new WrongGameRoundException();
        else if (response.statusCode() == 402)
            throw new Exception((String) response.body());

        return null;
    }

    public void restart() {
        request.put("/http/restartGame");
    }

    /** Cache Supplier Methods */

    private String currentPlayerSupplier() {
        HttpResponse<String> response = request.get("/http/currentPlayer");
        if (response.statusCode() == 200)
            return (String) response.body();

        return null;
    }

    private String gamePhaseSupplier() {
        HttpResponse<String> response = request.get("/http/game/phase");
        if (response.statusCode() == 200)
            return (String) response.body();

        return null;
    }

    /** TCP Package Listener and Publisher */

    @SuppressWarnings("unchecked")
    public void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    try {
                        BroadcastPackage incoming = ((BroadcastPackage) in.readObject());
                        BroadcastAction action = incoming.getAction();

                        if (action == BroadcastAction.CLIENT_CONNECTED) {
                            id = ((DynamicTypeValue<Integer>) (incoming.get("id"))).getValue().intValue();

                        }

                        publishBroadcastListener(action, incoming.getPayload());

                    } catch (IOException e) {
                        e.printStackTrace();
                        shutdown();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        shutdown();
                    }
                }

            }
        }).start();
        ;
    }

    /** Utilities */

    public int getId() {
        return this.id;
    }

    public Cache getCache() {
        return cache;
    }

    public void shutdown() {
        try {
            if (this.socket != null)
                socket.close();
            if (this.in != null)
                in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBroadcastListener(IBroadcastListener listener) {
        if (!this.broadcastListeners.contains(listener))
            this.broadcastListeners.add(listener);
    }

    public void removeBroadcastListener(IBroadcastListener listener) {
        if (this.broadcastListeners.contains(listener))
            this.broadcastListeners.remove(listener);
    }

    public void publishBroadcastListener(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        for (var l : this.broadcastListeners)
            l.onBroadcast(action, payload);
    }
}
