package net.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.DeductionToken;

public class JON {
    public static String build(Map<String, String> data) {
        StringBuilder body = new StringBuilder("");
        data.forEach((k, v) -> body.append(String.format("%s:%s\n", k, v)));

        return body.toString();
    }

    public static String build(List<String> data) {
        return String.join(":", data);
    }

    public static String build(int[][] data) {
        StringBuilder body = new StringBuilder();
        for(int i = 0; i<data.length; i++){
            for(int j = 0; j<data[i].length; j++){
                body.append(data[i][j]);
                if (j != (data[i].length-1)){
                    body.append(" ");
                }
            }
            if (i != data.length - 1){
                body.append('\n');
            }

        }
        return body.toString();
    }

    public static String build(ArrayList<Integer> data){
        return data.toString();
    }

    public static String build(HashMap<String[], DeductionToken> data){
        StringBuilder body = new StringBuilder();
         data.forEach((k, v) -> body.append(String.format("%s,%s:%s\n", k[0], k[1], v.toString())));
        return body.toString();
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

     public static HashMap<String[], DeductionToken> parseMapStringArrayDeductionToken(String requestBody) {
        HashMap<String [], DeductionToken> data = new HashMap<String [], DeductionToken>();

        for (var line : requestBody.split("\n")) {
            if (line.trim().equals("")) continue;
            String[] kv = line.trim().split(":");
            String[] k = kv[0].trim().split(",");
            DeductionToken token = DeductionToken.valueOf(kv[1]);
            data.put(k, token);
        }
        return data;
    }
    
    public static List<String> parseList(String requestBody) {
        List<String> parsed = new ArrayList<>();
        for (String token : requestBody.split(":"))
            if (!token.equals("")) parsed.add(token);

        return parsed;
    }

    public static ArrayList<Integer> parseListInt(String requestBody) {
        requestBody = requestBody.replace("[", "");
        requestBody = requestBody.replace("]", "");
        requestBody.trim();
        ArrayList<Integer> parsed = new ArrayList<>();
        for (String token : requestBody.split(","))
            if (!token.equals("")) parsed.add(Integer.parseInt(token.trim()));

        return parsed;
    }

    public static int[][] parseMatrix(String requestBody) {
        List<String> lines = Arrays.asList(requestBody.split("\n"));
        lines  = lines.stream().filter(line -> !line.trim().equals("")).toList();
        int rowCount = lines.size();
        int colCount = lines.get(0).split(" ").length;

        int[][] parsed = new int[rowCount][colCount];

        for(int i = 0; i<rowCount; i++){
            List<Integer> lineData = Arrays.asList(lines.get(i).split(" ")).stream().map(n -> Integer.parseInt(n)).toList();
            for(int j = 0; j<colCount; j++){
                parsed[i][j] = lineData.get(j).intValue();
               
            }
        }
        return parsed;
    }
}
