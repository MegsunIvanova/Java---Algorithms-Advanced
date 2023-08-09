package theme_02_GraphsBellmanFordAndLongestPathInDAG.Exercise;

import java.util.*;
import java.util.stream.Collectors;

public class P02_ModifiedKruskalAlgorithm {
    public static Map<Integer, List<Edge>> graph = new LinkedHashMap<>();

    public static int parents[];

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
            return String.format("(%d %d) -> %d", this.from, this.to, this.weight);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edgesCount = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);

            graph.putIfAbsent(from, new ArrayList<>());
            graph.putIfAbsent(to, new ArrayList<>());
            graph.get(from).add(edge);

        }

        parents = new int[nodes];

        for (Integer node : graph.keySet()) {
            parents[node] = node;
        }

        PriorityQueue<Edge> edgesQueue = graph.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(PriorityQueue::new));

        List<Edge> forest = new ArrayList<>();

        int forestWeight = 0;

        while (!edgesQueue.isEmpty()) {
            Edge minEdge = edgesQueue.poll();

            int firstRoot = findRoot(minEdge.from);
            int secondRoot = findRoot(minEdge.to);

            if (firstRoot != secondRoot) {
                forest.add(minEdge);
                forestWeight += minEdge.weight;
                parents[secondRoot] = firstRoot;

                for (int i = 0; i < parents.length; i++) {
                    if (parents[i] == secondRoot) {
                        parents[i] = firstRoot;
                    }
                }
            }
        }

//        int forestWeight = forest.stream().map(e -> e.weight).mapToInt(Integer::intValue).sum();

        System.out.println("Minimum spanning forest weight: " + forestWeight);

//        System.out.println(forest.stream()
//                .map(Edge::toString)
//                .collect(Collectors.joining(System.lineSeparator())));

    }

    private static int findRoot(int node) {

        while (parents[node] != node) {
            node = parents[node];
        }

        return node;
    }
}
