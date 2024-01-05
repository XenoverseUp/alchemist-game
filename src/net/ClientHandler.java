package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import enums.StringRequest;
import enums.StringResponse;
import domain.TheAlchemistGame;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private RequestParser requestParser;
    private TheAlchemistGame game;
    private final int id;

    public ClientHandler(Socket socket, TheAlchemistGame game) {
        this.socket = socket;
        this.requestParser = new RequestParser(this, game);
        
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            shutdown();
        }

        this.id = clientHandlers.size();
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                String request = bufferedReader.readLine();
                requestParser.parseAndExecute(request);
            } catch (IOException e) {
                shutdown();
                break;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }

    public void respond(StringResponse responseType, String response) {
        try {
            if (!socket.isClosed()) {
                bufferedWriter.write(responseType.toString() + "~" + response);
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
