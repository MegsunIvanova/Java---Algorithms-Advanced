package theme_02_GraphsBellmanFordAndLongestPathInDAG.Exercise;

import java.util.*;
import java.util.stream.Collectors;

public class P01_CableNetwork {

    public static Map<Integer, List<Edge>> graph = new LinkedHashMap<>();
    public static int cost = 0;

    public static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return String.format("%d -> %d, %d", this.from, this.to, this.weight);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int budget = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edgesCount = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        PriorityQueue<Edge> queue = new PriorityQueue<>();

        boolean[] used = new boolean[nodes];

        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(edge);

            if (tokens.length == 4) {
                used[from] = used[to] = true;
            }
        }

        prim(used, budget);


        System.out.println("Budget used: " + cost);
    }

    private static boolean prim(boolean[] used, int budget) {

        PriorityQueue<Edge> edgesQueue = graph.values()
                .stream().flatMap(List::stream)
                .filter(e -> (used[e.from] && !used[e.to]) || (!used[e.from] && used[e.to]))
                .collect(Collectors.toCollection(PriorityQueue::new));

        while (!edgesQueue.isEmpty()) {
            Edge minEdge = edgesQueue.poll();
            int from = minEdge.from;
            int to = minEdge.to;
            int weight = minEdge.weight;

            int removedValue = -1;

            int node = -1;

            if (used[from] && !used[to]) {
                used[to] = true;
                removedValue = weight;

            } else if (!used[from] && used[to]) {
                used[from] = true;
                removedValue = weight;
            }

            edgesQueue = graph.values()
                    .stream().flatMap(List::stream)
                    .filter(e -> (used[e.from] && !used[e.to]) || (!used[e.from] && used[e.to]))
                    .collect(Collectors.toCollection(PriorityQueue::new));


            if (removedValue != -1 && budget - removedValue > 0) {
                budget -= removedValue;
                cost += removedValue;
            } else if (budget - removedValue < 0) {
                return false;
            }

        }

        return true;
    }

}
