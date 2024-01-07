package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import domain.TheAlchemistGame;
import enums.BroadcastAction;
import interfaces.IDynamicTypeValue;
import net.util.DynamicTypeValue;

public class ClientHandler  {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    private Socket socket;
    private ObjectOutputStream out;
    private TheAlchemistGame game;
    private final int id;

    public ClientHandler(Socket socket, TheAlchemistGame game) {
        this.socket = socket;
        this.game = game;
        
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            shutdown();
        }

        this.id = clientHandlers.size();
        clientHandlers.add(this);

        BroadcastPackage p = new BroadcastPackage(
            BroadcastAction.CLIENT_CONNECTED, 
            new HashMap<String, IDynamicTypeValue>() {{
                put("id", new DynamicTypeValue<Integer>(id));
            }}
        );

        broadcast(p);
    }

    public int getId() {
        return id;
    }

    public static synchronized void broadcast(BroadcastPackage p) {
        for (ClientHandler c : clientHandlers) c.emit(p);
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }


    public void emit(BroadcastPackage response) {
        try {
            if (!socket.isClosed()) {
                out.writeObject(response);
                out.flush();
            }
        } catch (Exception e) {
            shutdown() ;
        }
    }

    public void shutdown() {
        removeClientHandler();
        try {
            if (socket != null) socket.close();
            if (out != null) out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
