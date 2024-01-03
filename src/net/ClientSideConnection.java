package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import enums.Request;
import enums.Response;
import error.HostDoesNotExistsException;

public class ClientSideConnection {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private ResponseParser responseParser;
    private int id;

    public ClientSideConnection(int port) throws HostDoesNotExistsException {
        try {
            this.socket = new Socket("localhost", port);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dataIn = new DataInputStream(this.socket.getInputStream());
            this.dataOut = new DataOutputStream(this.socket.getOutputStream());
            send(Request.GET_ID);
        } catch (IOException e) {
            shutdown();
            throw new HostDoesNotExistsException();
        }

        this.responseParser = new ResponseParser(this);
    }

    public void send(Request request) {
        try {
            if (!socket.isClosed()) {
                bufferedWriter.write(request.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            shutdown() ;
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
                        Response responseType = responseParser.parseResponseType(rawResponse);

                        switch (responseType) {
                            case ID:
                                id = responseParser.parseId(rawResponse);
                                break;
                            default:
                                break;
                        }

                        
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
