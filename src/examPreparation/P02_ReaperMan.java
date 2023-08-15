package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class P02_ReaperMan {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int vertices = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());

        int[] input = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int source = input[0];
        int destination = input[1];

        int[][] graph = new int[vertices][vertices];

        int[] distances = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        boolean[] visited = new boolean[vertices];

        int[] parents = new int[vertices];
        Arrays.fill(parents, -1);

        while (edges-- > 0) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int from = tokens[0];
            int to = tokens[1];
            int distance = tokens[2];

            graph[from][to] = distance;
            graph[to][from] = distance;
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(e -> distances[e]));
        queue.offer(source);

        while (!queue.isEmpty()) {
            Integer parent = queue.poll();
            visited[parent] = true;

            if (distances[parent] != Integer.MAX_VALUE) {
                for (int child = 0; child < graph[parent].length; child++) {
                    if (!visited[child] && graph[parent][child] != 0) {
                        queue.offer(child);

                        int newDistance = distances[parent] + graph[parent][child];
                        if (newDistance < distances[child]) {
                            distances[child] = newDistance;
                            parents[child] = parent;
                        }
                    }
                }
            }
        }

        int node = destination;
        List<Integer> path = new ArrayList<>();

        while (node != -1) {
            path.add(0, node);
            node = parents[node];
        }

        System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        System.out.println(distances[destination]);

    }
}
