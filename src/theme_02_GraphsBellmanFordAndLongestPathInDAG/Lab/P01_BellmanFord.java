package theme_02_GraphsBellmanFordAndLongestPathInDAG.Lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P01_BellmanFord {

    public static int[][] graph;
    public static int[] distance;
    public static int[] prev;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int edges = Integer.parseInt(scanner.nextLine());

        graph = new int[nodes + 1][nodes + 1];

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int destination = tokens[1];
            int weight = tokens[2];

            graph[source][destination] = weight;
        }

        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        try {
            bellmanFord(source);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }

        List<Integer> path = new ArrayList<>();

        path.add(destination);

        int node = prev[destination];

        while (node != -1) {
            path.add(0, node);
            node = prev[node];
        }

        System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        System.out.println(distance[destination]);
    }

    private static void bellmanFord(int sourceNode) {
        //Generic Shortest Path Algo

        //1) Init dist and prev
        distance = new int[graph.length];
        prev = new int[graph.length];

        //2) Set values to dist and prev
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        distance[sourceNode] = 0;

        //3)Relaxation steps:

        for (int i = 1; i < graph.length - 1; i++) {
            for (int r = 1; r < graph.length; r++) {
                for (int c = 1; c < graph[r].length; c++) {
                    int weight = graph[r][c];
                    if (weight != 0) {
                        int source = r;
                        int dest = c;

                        if (distance[source] != Integer.MAX_VALUE) {
                            int newValue = distance[source] + weight;
                            if (newValue < distance[dest]) {
                                distance[dest] = newValue;
                                prev[dest] = source;
                            }
                        }
                    }

                }
            }

        }

        for (int r = 1; r < graph.length; r++) {
            for (int c = 1; c < graph[r].length; c++) {
                int weight = graph[r][c];
                if (weight != 0) {
                    int source = r;
                    int dest = c;

                    if (distance[source] != Integer.MAX_VALUE) {
                        int newValue = distance[source] + weight;
                        if (newValue < distance[dest]) {
                            throw new IllegalStateException("Negative Cycle Detected");
                        }
                    }
                }

            }
        }


    }
}
