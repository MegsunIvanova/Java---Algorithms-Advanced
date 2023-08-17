package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class P03_TravelOptimizer {

    private static Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
    //Map<SourceNode, Map<DestNode, Weight>>

    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int countEdges = Integer.parseInt(reader.readLine());

        for (int i = 0; i < countEdges; i++) {
            int[] edge = Arrays.stream(reader.readLine().split(", "))
                    .mapToInt(Integer::parseInt).toArray();
            //"{start}, {end}, {weight}"
            int start = edge[0];
            int end = edge[1];
            int weight = edge[2];

            graph.putIfAbsent(start, new HashMap<>());
            graph.putIfAbsent(end, new HashMap<>());

            graph.get(start).put(end, weight);
            graph.get(end).put(start, weight);
        }

        int startNode = Integer.parseInt(reader.readLine());
        int destinationNode = Integer.parseInt(reader.readLine());
        int maxStops = Integer.parseInt(reader.readLine());

        visited = new boolean[graph.keySet().size()];

        int[] distances = new int[graph.keySet().size()];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startNode] = 0;

        int[] stops = new int[graph.keySet().size()];

        int[] prev = new int[graph.keySet().size()];
        Arrays.fill(prev, -1);

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int node : graph.keySet()) {
            topologicalSort(node, stack);
        }

        boolean isStarted = false;

        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            if (node == startNode) {
                isStarted = true;
            }
            Map<Integer, Integer> edges = graph.get(node);
            for (Integer child : edges.keySet()) {
                int weight = edges.get(child);
                if (distances[node] != Integer.MAX_VALUE
                        && (stops[node] < maxStops || (stops[node] == maxStops && child == destinationNode))
                        && distances[node] + weight < distances[child]) {

                    distances[child] = distances[node] + weight;
                    prev[child] = node;
                    if (isStarted && node != destinationNode) {
                        stops[child] = stops[node] + 1;
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        int node = destinationNode;

        while (node != -1) {
            path.add(0, node);
            node = prev[node];
        }


        if (path.get(0) != startNode) {
            System.out.println("There is no such path.");
        } else {
            System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }


    }

    private static void topologicalSort(int node, ArrayDeque<Integer> stack) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        Map<Integer, Integer> edges = graph.get(node);
        for (Integer child : edges.keySet()) {
            topologicalSort(child, stack);
        }

        stack.push(node);
    }
}
