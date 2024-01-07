package net.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPRequestParser {
    public static String parseParameter(String originalPath, String template) {
        String[] templateTokens = template.split("/");

        for (int i = 0; 0 < templateTokens.length; i++) 
            if (isPlaceholder(templateTokens[i])) 
                return originalPath.split("/")[i];        

        return null;
    }

    public static String parsePath(String path, List<String> endpointPatterns) {
        String[] pathSegments = path.split("/");
        StringBuilder resultPath = new StringBuilder("/");

        ArrayList<List<String>> steps = new ArrayList<>(List.of(endpointPatterns));

        for (int i = 0; i < pathSegments.length; i++) {
            if (pathSegments[i].isEmpty()) continue;

            List<String> improved = new ArrayList<>();

            for (var endpoint : steps.get(steps.size() - 1)) {
                String[] endpointSegments = endpoint.split("/");
                if (pathSegments[i].equals(endpointSegments[i])) {
                    improved.add(endpoint);
                } else if (isPlaceholder(endpointSegments[i])) {
                    improved.add(endpoint);
                }
            }

            steps.add(improved);
        }

        // Choose the best match
        String bestMatch = chooseBestMatch(steps.get(steps.size() - 1), pathSegments.length);
        return bestMatch != null ? bestMatch : resultPath.toString();
    }

    public static Map<String, String> parseBody(String requestBody) {
        Map<String, String> data = new HashMap<String, String>();

        for (var line : requestBody.split("\n")) {
            if (line.trim().equals("")) continue;
            String[] kv = line.trim().split(":");
            data.put(kv[0], kv[1]);
        }
        
        return data;
    }

    private static boolean isPlaceholder(String str) {
        return str.startsWith(":");
    }

    private static String chooseBestMatch(List<String> candidates, int pathLength) {
        for (String candidate : candidates) {
            String[] segments = candidate.split("/");
            if (segments.length == pathLength) {
                return candidate;
            }
        }
        return null;
    }
}
