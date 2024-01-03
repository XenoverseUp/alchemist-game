package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import domain.TheAlchemistGame;

public class Server {
    private ServerSocket serverSocket;
    private TheAlchemistGame game;

    public Server(ServerSocket ss, TheAlchemistGame game) {
        this.serverSocket = ss;
        this.game = game;
        System.out.println(String.format("Game Server is up and running on http://localhost:%d", serverSocket.getLocalPort()));

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
                        
                        ClientHandler clientHandler = new ClientHandler(clientSocket, game);
                        
                        Thread thread = new Thread(clientHandler);
                        thread.start();
                    }

                    System.out.println("Game Server: Everyone is ready. Starting the game session.");
                } catch (IOException e) {
                    closeServer();
                }
            }    
        }).start();;
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
