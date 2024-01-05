package net;

import enums.Response;

public class ResponseParser {
    private ClientSideConnection csc;

    public ResponseParser(ClientSideConnection csc) {
        this.csc = csc;
    }

    public Response parseResponseType(String rawResponse) {
        String[] tokens = rawResponse.split("~");

        for (Response r : Response.values())
            if (tokens[0].equals(r.toString())) return r;

        return null;
    }

    public int parseId(String rawResponse) {
        String[] tokens = rawResponse.split("~");

        return Integer.parseInt(tokens[1]);
    }
}   
