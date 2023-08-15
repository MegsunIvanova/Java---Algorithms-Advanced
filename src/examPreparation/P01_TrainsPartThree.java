package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P01_TrainsPartThree {

    private static int[][] graph;
    private static int[] parents;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());

        int[] inputLine = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int source = inputLine[0];
        int destination = inputLine[1];

        graph = new int[nodes][nodes];
        parents = new int[nodes];

        while (edges-- > 0) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int from = tokens[0];
            int to = tokens[1];
            int capacity = tokens[2];

            graph[from][to] = capacity;
        }

        int maxFlow = 0;

        while (bfs(source, destination)) {
            //find minFlow for path
            int minFlow = Integer.MAX_VALUE;

            int node = destination;
            while (node != source) {
                minFlow = Math.min(graph[parents[node]][node], minFlow);
                node = parents[node];
            }

            //add minFlow to max flow
            maxFlow += minFlow;

            //adjust graph
            node = destination;
            while (node != source) {
                graph[parents[node]][node] -= minFlow;
                graph[node][parents[node]] += minFlow;
                node = parents[node];
            }
        }

        System.out.println(maxFlow);
    }


    private static boolean bfs(int source, int destination) {
        boolean[] visited = new boolean[graph.length];

        Arrays.fill(parents, -1);

        Deque<Integer> queue = new ArrayDeque<>();

        visited[source] = true;
        queue.offer(source);

        while (!queue.isEmpty()) {
            Integer node = queue.poll();

            for (int child = 0; child < graph[node].length; child++) {
                if (!visited[child] && graph[node][child] > 0) {
                    queue.offer(child);
                    visited[child] = true;
                    parents[child] = node;

                    if (child == destination) {
                        return true;
                    }
                }
            }
        }

        return visited[destination];
    }
}
