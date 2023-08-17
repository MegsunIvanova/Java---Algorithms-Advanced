package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P02_DataTransfer {

    private static Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
    //Map<from, Map<to, volume>> graph
    private static int[] parents;

    private static int nodes;
    private static int source;
    private static int dest;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        nodes = Integer.parseInt(reader.readLine());
        int connections = Integer.parseInt(reader.readLine());

        int[] line = Arrays.stream(reader.readLine().split("\\s++"))
                .mapToInt(Integer::parseInt).toArray();

        source = line[0];
        dest = line[1];

        for (int i = 0; i < connections; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s++"))
                    .mapToInt(Integer::parseInt).toArray();

            int from = tokens[0];
            int to = tokens[1];
            int volume = tokens[2];

            graph.putIfAbsent(from, new HashMap<>());
            graph.get(from).put(to, volume);
        }

        parents = new int[nodes];

        int maxFlow = 0;

        while (bfs()) {
            //find min flow path
            int node = dest;
            int flow = Integer.MAX_VALUE;
            while (node != source) {
                flow = Math.min(flow, graph.get(parents[node]).get(node));
                node = parents[node];
            }

            //add min flow to max flow
            maxFlow += flow;

            //adjust graph
            node = dest;
            while (node != source) {
                int newVolume = graph.get(parents[node]).get(node) - flow;
                graph.get(parents[node]).put(node, newVolume);
                node = parents[node];
            }

        }

        System.out.println(maxFlow);

    }

    private static boolean bfs() {
        Deque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[nodes];
        Arrays.fill(parents, -1);

        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            Map<Integer, Integer> edges = graph.get(node);
            if (edges != null) {
                for (Integer child : edges.keySet()) {
                    int volume = edges.get(child);
                    if (volume > 0 && !visited[child]) {
                        queue.offer(child);
                        visited[child] = true;
                        parents[child] = node;
                    }
                }
            }
        }

        return visited[dest];
    }
}
