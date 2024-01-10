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
import enums.GamePhase;
import error.HostDoesNotExistsException;
import error.NotEnoughActionsException;
import error.ServerSideException;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import net.http.HTTPClient;
import net.util.Cache;
import net.util.DynamicTypeValue;
import net.util.JON;

public class Client {
    private int id;
    private Socket socket;
    private ObjectInputStream in;
    private HTTPClient httpClient;
    private ArrayList<IBroadcastListener> broadcastListeners = new ArrayList<IBroadcastListener>();
    private Cache cache = new Cache();


    public Client(int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket("localhost", port);
            this.in = new ObjectInputStream(socket.getInputStream());
            this.httpClient = HTTPClient.getInstance();
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
            this.httpClient = HTTPClient.getInstance();
            this.httpClient.setHost(host);
            setupCache();
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

    }

    private void setupCache() {
        cache.create("current-player", this::currentPlayerSupplier);
        cache.create("game-phase", this::gamePhaseSupplier);
    }

    /** HTTP Methods */

    public int createUser(int id, String name, Avatar avatar) {
        String body = JON.build(new HashMap<String, String>() {{
            put("name", name);
            put("id", String.valueOf(id));
            put("avatar", avatar.toString());
        }});

        HttpResponse<String> response = httpClient.post("/http/createPlayer", body);

        if (response.statusCode() == 200) return 0;
        else return Integer.parseInt((String)response.body());
    }

    public Map<String, String> getPlayerNames() {
        HttpResponse<String> response = httpClient.get("/http/game/players");
        Map<String, String> data = JON.parseMap((String)response.body());
        
        return data;
    }

    public Avatar getAvatar(int id) {
        HttpResponse<String> response = httpClient.get(String.format("/http/playerAvatar/%d", id));

        if (response.statusCode() == 200) return Avatar.valueOf((String)response.body());
        return null;
    }

    public void startGame() throws ServerSideException {
        String body = JON.build(new HashMap<String, String>() {{
            put("id", String.valueOf(id));
        }});

        HttpResponse<String> response = httpClient.put("/http/startGame", body);

        if (response.statusCode() != 200) throw new ServerSideException();
    }

    public Map<String, String> getCurrentUser(boolean cached) {
        if (!cached) cache.revalidate("current-player");
        String body = cache.get("current-player");

        return JON.parseMap(body);
    }
   
    public Map<String, String> getCurrentUser() {
        cache.revalidate("current-player");
        String body = cache.get("current-player");

        return JON.parseMap(body);
    }

    public GamePhase getPhase(boolean cached) {
        if (!cached) cache.revalidate("game-phase");
        String phase = cache.get("game-phase");

        return GamePhase.valueOf(phase);
    }

    public GamePhase getPhase() {
        cache.revalidate("game-phase");
        String phase = cache.get("game-phase");

        return GamePhase.valueOf(phase);
    }

    public void toggleCurrentUser() throws ServerSideException {
        HttpResponse<String> response = httpClient.put("/http/togglePlayer");
        if (response.statusCode() != 200) throw new ServerSideException();
    }

    public void forageIngredient() throws NotEnoughActionsException   {
        HttpResponse<String> response = httpClient.put("/http/forageIngredient");

        if (response.statusCode() == 400) throw new NotEnoughActionsException();
    }

    public int drawMysteryCard() throws NotEnoughActionsException {
        HttpResponse<String> response = httpClient.put("/http/drawMysteryCard");
        
        if (response.statusCode() == 400) throw new NotEnoughActionsException();
        else if (response.statusCode() == 200) return 0;
        else return Integer.parseInt((String)response.body());
    }


    public int buyArtifact(String name) throws NotEnoughActionsException {
        HttpResponse<String> response = httpClient.put("/http/buyArtifact", name);

        if (response.statusCode() == 400) throw new NotEnoughActionsException();
        else if (response.statusCode() == 200) return 0;
        else return Integer.parseInt((String)response.body());
    }

    public List<String> getCurrentPlayerArtifacts() {
        HttpResponse<String> response = httpClient.get("/http/inventory/artifact");

        if (response.statusCode() == 200)
            return JON.parseList((String)response.body());
        
        return null;
    }
    
    public List<String> getCurrentPlayerIngredients() {
        HttpResponse<String> response = httpClient.get("/http/inventory/ingredient");

        if (response.statusCode() == 200)
            return JON.parseList((String)response.body());
        
        return null;
    }
    
    public void transmuteIngredient(String name) throws NotEnoughActionsException {
        HttpResponse<String> response = httpClient.put("/http/transmuteIngredient", name);

        if (response.statusCode() == 400) throw new NotEnoughActionsException();
    }

    public void discardArtifact(String name) throws NotEnoughActionsException {
        HttpResponse<String> response = httpClient.put("/http/discardArtifact", name);

        if (response.statusCode() == 400) throw new NotEnoughActionsException();
    }


    /** Cache Supplier Methods */
    
    private String currentPlayerSupplier() {
        HttpResponse<String> response = httpClient.get("/http/currentPlayer");
        if (response.statusCode() == 200) 
            return (String)response.body();
        
        return null;
    }
   
    private String gamePhaseSupplier() {
        HttpResponse<String> response = httpClient.get("/http/game/phase");
        if (response.statusCode() == 200) 
            return (String)response.body();
        
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
                        BroadcastPackage incoming = ((BroadcastPackage)in.readObject());
                        BroadcastAction action = incoming.getAction();


                        switch (action) {
                            case PLAYER_CREATED:
                                break;
                            case CLIENT_CONNECTED:
                                id = ((DynamicTypeValue<Integer>)(incoming.get("id"))).getValue().intValue();
                                break;
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
        }).start();;
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
            if (this.socket != null) socket.close();
            if (this.in != null) in.close(); 
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