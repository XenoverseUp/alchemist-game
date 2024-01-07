package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import enums.BroadcastAction;
import error.HostDoesNotExistsException;
import net.util.DynamicTypeValue;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private int id;

    public Client(int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket("localhost", port);
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

    }

    public void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    try {
                        BroadcastPackage incoming = ((BroadcastPackage)in.readObject());
                        BroadcastAction action = incoming.getAction();
                        System.out.println(action);

                        switch (action) {
                            case USER_CREATED:

                                break;
                            case CLIENT_CONNECTED:
                                // id = ((DynamicTypeValue<Integer>)(incoming.get("id"))).getValue().intValue();
                                break;
                        }

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
}
