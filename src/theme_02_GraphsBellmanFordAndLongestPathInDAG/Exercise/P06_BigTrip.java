package theme_02_GraphsBellmanFordAndLongestPathInDAG.Exercise;

import java.util.*;
import java.util.stream.Collectors;

public class P06_BigTrip {
    public static int[][] graph;
    public static boolean[] visited;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int edges = Integer.parseInt(scanner.nextLine());

        graph = new int[nodes + 1][nodes + 1];

        for (int i = 0; i < edges; i++) {
            int[] edge = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            graph[edge[0]][edge[1]] = edge[2];
//            graph[edge[1]][edge[0]] = edge[2];
        }

        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        ArrayDeque<Integer> sortedVertices = new ArrayDeque<>();

        visited = new boolean[graph.length];

        for (int i = 1; i <= nodes; i++) {
            topologicalSort(i, sortedVertices);
        }

        int[] distances = new int[graph.length];
        Arrays.fill(distances, Integer.MIN_VALUE);
        distances[source] = 0;

        int[] prev = new int[graph.length];
        Arrays.fill(prev, -1);

        while (!sortedVertices.isEmpty()) {
            Integer node = sortedVertices.pop();
            for (int i = 1; i < graph[node].length; i++) {
                int weight = graph[node][i];
                int newDistance = distances[node] + weight;
                if (weight != 0 && distances[node] != Integer.MIN_VALUE && newDistance > distances[i]) {
                    distances[i] = newDistance;
                    prev[i] = node;
                }
            }
        }

        List<Integer> path = new ArrayList<>();

        int current = destination;
        path.add(current);

        while (current != -1 && current != source) {
            current = prev[current];
            if (current != -1) {
                path.add(0, current);
            }
        }

        System.out.println(distances[destination]);

        System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));

    }

    private static void topologicalSort(int node, ArrayDeque<Integer> sortedVertices) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        for (int i = 1; i < graph[node].length; i++) {
            if (graph[node][i] != 0) {
                topologicalSort(i, sortedVertices);
            }
        }

        sortedVertices.push(node);
    }


}
