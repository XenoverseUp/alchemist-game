package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import error.HostDoesNotExistsException;

public class ClientSideConnection {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private int id;

    public ClientSideConnection(int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket("localhost", port);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dataIn = new DataInputStream(this.socket.getInputStream());
            this.dataOut = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

    }

    public void send(String request) {
        try {
            if (!socket.isClosed()) {
                bufferedWriter.write(request);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            shutdown();
        }
    }

    public void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Handle broadcasting with prefix.

                while (!socket.isClosed()) {
                    try {
                        String rawResponse = bufferedReader.readLine();
                        String[] tokens = rawResponse.split("~");

                        if (tokens[0].equals("id")) id = Integer.parseInt(tokens[1]);

                    } catch (IOException e) {
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
            if (this.bufferedReader != null) bufferedReader.close();
            if (this.bufferedWriter != null) bufferedReader.close();
            if (this.dataIn != null) dataIn.close();
            if (this.dataOut != null) dataOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
