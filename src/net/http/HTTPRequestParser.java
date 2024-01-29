package net.http;

import java.util.ArrayList;
import java.util.List;

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
