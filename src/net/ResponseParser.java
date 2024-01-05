package net;

import enums.StringResponse;

public class ResponseParser {
    private ClientSideConnection csc;

    public ResponseParser(ClientSideConnection csc) {
        this.csc = csc;
    }

    public StringResponse parseResponseType(String rawResponse) {
        String[] tokens = rawResponse.split("~");

        for (StringResponse r : StringResponse.values())
            if (tokens[0].equals(r.toString())) return r;

        return null;
    }

    public int parseId(String rawResponse) {
        String[] tokens = rawResponse.split("~");

        return Integer.parseInt(tokens[1]);
    }
}   
