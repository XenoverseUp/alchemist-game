package net;

import java.net.Socket;

import domain.TheAlchemistGame;
import enums.StringRequest;
import enums.StringResponse;

public class RequestParser {
    private TheAlchemistGame game;
    private ClientHandler handler;
    
    public RequestParser(ClientHandler clientHandler, TheAlchemistGame game) {
        this.game = game;
        this.handler = clientHandler;
    }

    public void parseAndExecute(String request) {
        if (request.equals(StringRequest.GET_ID.toString())) {
            handler.respond(StringResponse.ID, String.valueOf(handler.getId()));
            return;
        };
        
    }
}
