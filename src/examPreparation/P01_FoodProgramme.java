package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class P01_FoodProgramme {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int vertices = Integer.parseInt(reader.readLine());
        int roads = Integer.parseInt(reader.readLine());

        int[] inputLine = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();

        int start = inputLine[0];
        int target = inputLine[1];

        //Map<Source, Map<Dest, Time>> graph
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();

        for (int i = 0; i < roads; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();

            int first = tokens[0];
            int second = tokens[1];
            int time = tokens[2];

            graph.putIfAbsent(first, new HashMap<>());
            graph.putIfAbsent(second, new HashMap<>());

            graph.get(first).put(second, time);
            graph.get(second).put(first, time);
        }

        int[] distances = new int[vertices];
        int[] prev = new int[vertices];
        boolean[] visited = new boolean[vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        distances[start] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(node -> distances[node]));

        queue.offer(start);

        while (!queue.isEmpty()) {
            Integer parent = queue.poll();
            visited[parent] = true;

            Map<Integer, Integer> edges = graph.get(parent);

            for (Integer childNode : edges.keySet()) {
                if (!visited[childNode]) {
                    queue.offer(childNode);
                    int newDistance = distances[parent] + edges.get(childNode);
                    if (newDistance < distances[childNode]) {
                        distances[childNode] = newDistance;
                        prev[childNode] = parent;
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
