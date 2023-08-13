package theme_03_GraphsStronglyConnectedComponentsMaxFlow.Exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class P02_FindBiConnectedComponents {
    private static int[][] graph;
    private static int[] depths;
    private static int[] lowPoints;
    private static int[] parents;
    private static int[] reachableCount;
    private static List<List<Integer>> subGraphs = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        graph = new int[nodes][nodes];
        depths = new int[graph.length];
        lowPoints = new int[graph.length];
        parents = new int[graph.length];
        reachableCount = new int[graph.length];

        Arrays.fill(parents, -1);

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int destination = tokens[1];

            graph[source][reachableCount[source]++] = destination;
            graph[destination][reachableCount[destination]++] = source;
        }

        findArticulationPoints(0, 0, new ArrayList<>());

        System.out.println("Number of bi-connected components: " + subGraphs.size());
    }

    private static void findArticulationPoints(int node, int depth, List<Integer> subSet) {
        depths[node] = depth;
        lowPoints[node] = depth;

        for (int i = 0; i < reachableCount[node]; i++) {
            int child = graph[node][i];
            if (depths[child] == 0) {
                parents[child] = node;
                List<Integer> components = new ArrayList<>();
                findArticulationPoints(child, depth + 1, components);
                if (lowPoints[child] >= depths[node] || parents[child] == -1) {
                    components.add(node);
                    subGraphs.add(components);
                } else {
                    subSet.addAll(components);
                }

                lowPoints[node] = Math.min(lowPoints[node], lowPoints[child]);

            } else if (child != parents[node]) {
                lowPoints[node] = Math.min(lowPoints[node], depths[child]);
            }
        }

        subSet.add(node);
    }
}
