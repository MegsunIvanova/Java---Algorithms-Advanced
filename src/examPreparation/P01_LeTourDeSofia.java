package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P01_LeTourDeSofia {
    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static boolean[] visited;
    private static int distances[];

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());
        int source = Integer.parseInt(reader.readLine());

        visited = new boolean[nodes];
        distances = new int[nodes];

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();

            graph.putIfAbsent(tokens[0], new ArrayList<>());
            graph.get(tokens[0]).add(tokens[1]);
        }

        bfs(source);

        if (distances[source] != 0) {
            System.out.println(distances[source]);
        } else {
            int visitedCount = 0;
            for (boolean node : visited) {
                if (node) {
                    visitedCount++;
                }
            }
            System.out.println(visitedCount);
        }
    }

    private static void bfs(int source) {
        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(source);

        visited[source] = true;
        distances[source] = 0;

        while (!queue.isEmpty()) {
            int parent = queue.poll();
            List<Integer> children = graph.get(parent);
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    int child = children.get(i);
                    if (!visited[child]) {
                        visited[child] = true;
                        queue.offer(child);
                        distances[child] = distances[parent] + 1;
                    } else if (child == source && distances[child] == 0) {
                        distances[child] = distances[parent] + 1;
                        return;
                    }
                }
            }

        }
    }
}

