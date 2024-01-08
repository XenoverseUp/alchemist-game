package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import enums.Avatar;
import enums.BroadcastAction;
import error.HostDoesNotExistsException;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import net.http.HTTPClient;
import net.util.DynamicTypeValue;
import net.util.JON;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private int id;
    private ArrayList<IBroadcastListener> broadcastListeners = new ArrayList<IBroadcastListener>();
    private HTTPClient httpClient;

    public Client(int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket("localhost", port);
            this.in = new ObjectInputStream(socket.getInputStream());
            this.httpClient = HTTPClient.getInstance();
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

    }

    /** HTTP Methods */

    public int createUser(int id, String name, Avatar avatar) {
        String body = httpClient.buildBody(new HashMap<String, String>() {{
            put("name", name);
            put("id", String.valueOf(id));
            put("avatar", avatar.toString());
        }});

        HttpResponse<String> response = httpClient.post("/http/createPlayer", body);

        if (response.statusCode() == 200) return 0;
        else return Integer.parseInt((String)response.body());
    }

    public Map<String, String> getPlayerNames() {
        HttpResponse<String> response = httpClient.get("/http/players");
        Map<String, String> data = JON.parse((String)response.body());
        
        return data;
    }

    public Avatar getAvatar(int id) {
        HttpResponse<String> response = httpClient.get(String.format("/http/playerAvatar/%d", id));

        if (response.statusCode() == 200) return Avatar.valueOf((String)response.body());
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

    public int getId() {
        return this.id;
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
