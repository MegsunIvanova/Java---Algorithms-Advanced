package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P02_Picker {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());
        int edgesCount = Integer.parseInt(reader.readLine());

        int[][] graph = new int[nodes][nodes];

        for (int i = 0; i < edgesCount; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s++"))
                    .mapToInt(Integer::parseInt).toArray();

            graph[tokens[0]][tokens[1]] = tokens[2];
        }


        PriorityQueue<int[]> edges = new PriorityQueue<>(Comparator.comparingInt(e -> graph[e[0]][e[1]]));

        for (int r = 0; r < graph.length; r++) {
            for (int c = 0; c < graph[r].length; c++) {
                if (graph[r][c] > 0) {
                    edges.offer(new int[]{r, c});
                }
            }
        }

        int[] parents = new int[nodes];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }

        StringBuilder forest = new StringBuilder();
        int totalWeight = 0;

        while (!edges.isEmpty()) {
            int[] edge = edges.poll();

            int source = edge[0];
            int dest = edge[1];

            int firstRoot = findRoot(source, parents);
            int secondRoot = findRoot(dest, parents);

            if (firstRoot != secondRoot) {
                forest.append(source).append(" ").append(dest).append(System.lineSeparator());
                totalWeight += graph[source][dest];
                parents[secondRoot] = firstRoot;
            }
        }

        System.out.print(forest);
        System.out.println(totalWeight);

    }

    private static int findRoot(int node, int[] parents) {
        while (parents[node] != node) {
            node = parents[node];
        }

        return node;
    }
}
