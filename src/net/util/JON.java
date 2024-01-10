package net.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JON {
    public static String build(Map<String, String> data) {
        StringBuilder body = new StringBuilder("");
        data.forEach((k, v) -> body.append(String.format("%s:%s\n", k, v)));

        return body.toString();
    }

    public static String build(List<String> data) {
        return String.join(":", data);
    }
   
    public static Map<String, String> parseMap(String requestBody) {
        Map<String, String> data = new HashMap<String, String>();

        for (var line : requestBody.split("\n")) {
            if (line.trim().equals("")) continue;
            String[] kv = line.trim().split(":");
            data.put(kv[0], kv[1]);
        }
        
        return data;
    }
    
    public static List<String> parseList(String requestBody) {
        List<String> parsed = new ArrayList<>();
        for (String token : requestBody.split(":"))
            if (!token.equals("")) parsed.add(token);

        return parsed;
    }

}
