package theme_02_GraphsBellmanFordAndLongestPathInDAG.Exercise;

import java.util.*;
import java.util.stream.Collectors;

public class P03_MostReliablePath {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        String[] path = scanner.nextLine().split("\\s+");
        int source = Integer.parseInt(path[1]);
        int destination = Integer.parseInt(path[3]);

        int edgesCount = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        int[][] graph = new int[nodes][nodes];

        for (int i = 0; i < edgesCount; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int firstNode = tokens[0];
            int secondNode = tokens[1];
            int weight = tokens[2];

            graph[firstNode][secondNode] = weight;
            graph[secondNode][firstNode] = weight;
        }

        double[] distances = new double[nodes];
        distances[source] = 100.00;

        boolean[] visited = new boolean[nodes];

        int prev[] = new int[nodes];
        Arrays.fill(prev, -1);

        PriorityQueue<Integer> queue = new PriorityQueue<>((f, s) -> Double.compare(distances[s], distances[f]));

        queue.offer(source);

        while (!queue.isEmpty()) {
            int minNode = queue.poll();

            visited[minNode] = true;

            for (int i = 0; i < graph[minNode].length; i++) {
                int weight = graph[minNode][i];

                if (weight != 0 && !visited[i]) {

                    double newDistance = distances[minNode] * weight / 100.00;

                    if (newDistance > distances[i]) {
                        distances[i] = newDistance;
                        prev[i] = minNode;
                    }

                    queue.offer(i);
                }
            }
        }

        System.out.printf("Most reliable path reliability: %.2f%%%n", distances[destination]);

        int node = destination;
        List<Integer> nodesPath = new ArrayList<>();
        nodesPath.add(0, destination);

        while (node != source && node != -1) {
            node = prev[node];
            nodesPath.add(0, node);
        }

        System.out.println(nodesPath.stream().map(String::valueOf).collect(Collectors.joining(" -> ")));
    }
}
