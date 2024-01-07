package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import domain.TheAlchemistGame;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private TheAlchemistGame game;
    private final int id;

    public ClientHandler(Socket socket, TheAlchemistGame game) {
        this.socket = socket;
        this.game = game;
        
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            shutdown();
        }

        this.id = clientHandlers.size();
        clientHandlers.add(this);
        emit(String.format("id~%d", this.id));
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                String request = bufferedReader.readLine();
            } catch (IOException e) {
                shutdown();
                break;
            }
        }
    }

    public int getId() {
        return id;
    }

    public static void broadcast(Package p) {
        for (ClientHandler c : clientHandlers) c.emit(p);
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }

    public void emit(String response) {
        try {
            if (!socket.isClosed()) {
                bufferedWriter.write(response);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            shutdown() ;
        }
    }

    public void emit(Package response) {
        try {
            if (!socket.isClosed()) {
                // bufferedWriter.write(response);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            shutdown() ;
        }
    }

    public void shutdown() {
        removeClientHandler();
        try {
            if (socket != null) socket.close();
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
