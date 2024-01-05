package net;

import java.net.Socket;

import domain.TheAlchemistGame;
import enums.Request;
import enums.Response;

public class RequestParser {
    private TheAlchemistGame game;
    private ClientHandler handler;
    
    public RequestParser(ClientHandler clientHandler, TheAlchemistGame game) {
        this.game = game;
        this.handler = clientHandler;
    }

    public void parseAndExecute(String request) {
        if (request.equals(Request.GET_ID.toString())) {
            handler.respond(Response.ID, String.valueOf(handler.getId()));
            return;
        };
        
    }
}
