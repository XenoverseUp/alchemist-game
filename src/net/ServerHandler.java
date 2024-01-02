package net;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import domain.Board;

public class ServerHandler implements NetworkingHandler {
    private Board board;
    private Socket socket;
    private ServerSocket ss;
    private DataInputStream in;
    private DataOutputStream out;
    
    public ServerHandler(int port, Board board) {
        this.board = board;

        try {
            ss = new ServerSocket(port);
            socket = ss.accept();
            System.out.println("Client accepted");
 
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
 
            String line = "";
 
            while (!line.equals("exit")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);
                } catch(IOException i) {
                    System.out.println(i);
                }
            }

            dispose();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    @Override
    public int getPlayerCount() {
        return board.getAuth().players.size();
    }

    @Override
    public void dispose() {
        System.out.println("Closing connection...");
        try {
            socket.close();
            in.close();
            out.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
