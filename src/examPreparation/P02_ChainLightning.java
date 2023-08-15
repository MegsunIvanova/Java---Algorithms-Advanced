package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P02_ChainLightning {
    private static List<Edge>[] graph;
    private static Map<Integer, List<Integer>> forest = new HashMap<>();
    private static boolean[] visited;
    private static int[] damage;
    private static int max;

    private static class Edge {
        private int from;
        private int to;
        private int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return String.format("%d -> %d (%d)", this.from, this.to, this.weight);
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());
        int strikesCount = Integer.parseInt(reader.readLine());

        visited = new boolean[nodes];

        graph = new ArrayList[nodes];

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();
            int from = tokens[0];
            int to = tokens[1];
            int weight = tokens[2];

            Edge edge = new Edge(from, to, weight);

            if (graph[from] == null) {
                graph[from] = new ArrayList<>();
            }

            if (graph[to] == null) {
                graph[to] = new ArrayList<>();
            }

            graph[from].add(edge);
            graph[to].add(edge);
        }

        for (int i = 0; i < nodes; i++) {
            msf(i);
        }

        List<int[]> strikes = new ArrayList<>();

        damage = new int[nodes];

        for (int i = 0; i < strikesCount; i++) {
            int[] tokens = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();
            int node = tokens[0];
            int power = tokens[1];

            hitNodes(node, node, power);

        }

        System.out.println(max);

    }

    private static void hitNodes(int node, int next, int power) {
        if (power < 1 || forest.get(node) == null) {
            return;
        }

        damage[node] += power;
        power /= 2;

        if (max < damage[node]) {
            max = damage[node];
        }

        for (int child : forest.get(node)) {
            if (child != next) {
                hitNodes(child, node, power);
            }
        }
    }

    private static void msf(int node) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        visitNodes(node, queue);

        while (!queue.isEmpty()) {
            Edge minEdge = queue.poll();

            int from = minEdge.from;
            int to = minEdge.to;

            if (visited[from] && visited[to]) {
                continue;
            }

            forest.putIfAbsent(from, new ArrayList<>());
            forest.putIfAbsent(to, new ArrayList<>());

            forest.get(from).add(to);
            forest.get(to).add(from);

            if (!visited[from]) {
                visitNodes(from, queue);
            } else if (!visited[to]) {
                visitNodes(to, queue);
            }
        }
    }

    private static void visitNodes(int node, PriorityQueue<Edge> queue) {
        visited[node] = true;

        if (graph[node] != null) {
            for (Edge edge : graph[node]) {
                int nextNode = node == edge.from ? edge.to : edge.from;
                if (!visited[nextNode]) {
                    queue.offer(edge);
                }
            }
        }
    }

}
