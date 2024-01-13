package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import domain.TheAlchemistGame;
import enums.BroadcastAction;
import net.http.HTTPServer;
import net.util.BroadcastPackage;

public class Server {
    private ServerSocket serverSocket;
    private TheAlchemistGame game;

    public Server(ServerSocket ss) {
        this.serverSocket = ss;
        this.game = new TheAlchemistGame();

        System.out.println(String.format("Game Server is up and running on http://localhost:%d...", serverSocket.getLocalPort()));
        try {
            new HTTPServer(this.game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (ClientHandler.clientHandlers.size() < 4) {
                        Socket clientSocket = serverSocket.accept();
                        if (ClientHandler.clientHandlers.size() == 0)
                            System.out.println("Server: Host is connected.");
                        else System.out.println(String.format("Server: Client #%s is connected.", ClientHandler.clientHandlers.size()));
                        
                        new ClientHandler(clientSocket);
                    }

                    System.out.println("Game Server: Everyone is ready. Starting the game session.");
                    ClientHandler.broadcast(new BroadcastPackage(BroadcastAction.ROOM_IS_FULL));
                } catch (IOException e) {
                    closeServer();
                }
            }    
        }).start();
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
