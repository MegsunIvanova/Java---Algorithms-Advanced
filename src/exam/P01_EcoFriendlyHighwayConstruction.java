package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class P01_EcoFriendlyHighwayConstruction {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int edgesCount = Integer.parseInt(reader.readLine());

        Map<String, Map<String, Integer>> graph = new HashMap<>();
        PriorityQueue<String[]> edges = new PriorityQueue<>(Comparator.comparingInt(e -> graph.get(e[0]).get(e[1])));
        Map<String, String> parents = new HashMap<>();

        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = reader.readLine().split("\\s+");
            String from = tokens[0];
            String to = tokens[1];
            int cost = Integer.parseInt(tokens[2]);

            if (tokens.length > 3) {
                cost += Integer.parseInt(tokens[3]);
            }

            graph.putIfAbsent(from, new HashMap<>());
            graph.get(from).put(to, cost);

            parents.put(from, from);
            parents.put(to, to);

            edges.offer(new String[]{from, to});
        }

        int totalCost = 0;
        while (!edges.isEmpty()) {
            String[] edge = edges.poll();

            String source = edge[0];
            String dest = edge[1];

            String firstRoot = findRoot(source, parents);
            String secondRoot = findRoot(dest, parents);

            if (!firstRoot.equals(secondRoot)) {
                totalCost += graph.get(source).get(dest);
                parents.put(secondRoot, firstRoot);
            }

        }

        System.out.println("Total cost of building highways: " + totalCost);

    }

    private static String findRoot(String node, Map<String, String> parents) {

        while (!parents.get(node).equals(node)) {
            node = parents.get(node);
        }

        return node;
    }
}
