package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class P01_VampireLabyrinth {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());

        int[] line = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();

        int start = line[0];
        int target = line[1];

        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        //Map<from, Map<to,vampiresCount>> graph

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();

            int from = tokens[0];
            int to = tokens[1];
            int vampiresCount = tokens[2];

            graph.putIfAbsent(from, new HashMap<>());
            graph.putIfAbsent(to, new HashMap<>());

            graph.get(from).put(to, vampiresCount);
            graph.get(to).put(from, vampiresCount);
        }

        int[] distances = new int[nodes];
        int[] prev = new int[nodes];
        boolean[] visited = new boolean[nodes];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        Arrays.fill(prev, -1);

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(node -> distances[node]));

        queue.offer(start);

        while (!queue.isEmpty()) {
            Integer parent = queue.poll();
            visited[parent] = true;

            Map<Integer, Integer> parentEdges = graph.get(parent);

            if (parentEdges != null) {
                for (int child : parentEdges.keySet()) {
                    if (!visited[child]) {
                        queue.offer(child);
                        int newDistance = distances[parent] + parentEdges.get(child);
                        if (newDistance < distances[child]) {
                            distances[child] = newDistance;
                            prev[child] = parent;
                        }
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();

        int node = target;

        while (node != -1) {
            path.add(0, node);
            node = prev[node];
        }

        System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        System.out.println(distances[target]);

    }
}
