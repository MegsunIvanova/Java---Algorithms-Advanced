package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class P01_TrainsPartTwo {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int vertices = Integer.parseInt(reader.readLine()); //number of depots
        int edges = Integer.parseInt(reader.readLine()); //the number of train tracks between all the depots

        int[] inputLine = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray(); // {start} {end}

        int source = inputLine[0];
        int destination = inputLine[1];

        int[][] graph = new int[vertices][vertices];
        int[] distances = new int[graph.length];
        int[] parents = new int[graph.length];
        boolean[] visited = new boolean[graph.length];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        Arrays.fill(parents, -1);

        while (edges-- > 0) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray(); // network in the format: {a} {b} {distance}
            graph[tokens[0]][tokens[1]] = tokens[2];
            graph[tokens[1]][tokens[0]] = tokens[2];
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(node -> distances[node]));
        queue.offer(source);

        while (!queue.isEmpty()) {
            Integer parent = queue.poll();
            visited[parent] = true;

            int[] children = graph[parent];

            for (int child = 0; child < children.length; child++) {
                if (children[child] != 0 && !visited[child]) {
                    queue.offer(child);
                    int newDistance = distances[parent] + children[child];
                    if(newDistance <= distances[child]) {
                        distances[child] = distances[parent] + children[child];
                        parents[child] = parent;
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();

        int node = destination;
        path.add(node);
        node = parents[node];

        while (node != -1) {
            path.add(0, node);
            node = parents[node];
        }

        System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        System.out.println(distances[destination]);
    }
}
