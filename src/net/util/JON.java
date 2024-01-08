package net.util;

import java.util.HashMap;
import java.util.Map;

public class JON {
    public static String build(Map<String, String> data) {
        StringBuilder body = new StringBuilder("");
        data.forEach((k, v) -> body.append(String.format("%s:%s\n", k, v)));

        return body.toString();
    }
   
    
    public static Map<String, String> parse(String requestBody) {
        Map<String, String> data = new HashMap<String, String>();

        for (var line : requestBody.split("\n")) {
            if (line.trim().equals("")) continue;
            String[] kv = line.trim().split(":");
            data.put(kv[0], kv[1]);
        }
        
        return data;
    }

}
