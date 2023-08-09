package theme_02_GraphsBellmanFordAndLongestPathInDAG.Exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P05_Undefined {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int edgesCount = Integer.parseInt(scanner.nextLine());

        int[][] graph = new int[nodes + 1][nodes + 1];
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < edgesCount; i++) {

            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            graph[tokens[0]][tokens[1]] = tokens[2];

            edges.add(new int[]{tokens[0], tokens[1]});
        }

        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        int[] distances = new int[graph.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        int[] prev = new int[graph.length];
        Arrays.fill(prev, -1);


        for (int i = 1; i < nodes; i++) {
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = graph[from][to];
                int newDistance = distances[from] + weight;

                if (distances[from] != Integer.MAX_VALUE
                        && newDistance < distances[to]) {
                    distances[to] = newDistance;
                    prev[to] = from;
                }
            }
        }

        boolean hasNegativeCycle = false;

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = graph[from][to];
            int newDistance = distances[from] + weight;

            if (distances[from] != Integer.MAX_VALUE
                    && newDistance < distances[to]) {
                hasNegativeCycle = true;
                break;
            }
        }

        if (hasNegativeCycle) {
            //                System.out.println("Negative Cycle Detected");
            System.out.println("Undefined");
        } else {
            List<Integer> path = new ArrayList<>();
            int node = destination;
            path.add(destination);

            while (node != -1 && node != source) {
                node = prev[node];
                path.add(0, node);
            }

            System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));

            System.out.println(distances[destination]);
        }
    }
}
