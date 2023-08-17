package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P01_DoraTheExplorer {
    private static Map<Integer, Map<Integer, Integer>> graph = new HashMap<>(); //Source<Dest, Minutes>
    private static Map<Integer, Integer> distances = new HashMap<>();
    private static Map<Integer, Integer> parents = new HashMap<>();
    private static Set<Integer> visited = new HashSet<>();
    private static PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(n -> distances.get(n)));

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int edges = Integer.parseInt(reader.readLine());

        while (edges-- > 0) {
            int[] tokens = Arrays.stream(reader.readLine().split(", ")).mapToInt(Integer::parseInt).toArray();
            int first = tokens[0];
            int second = tokens[1];
            int minutes = tokens[2];

            graph.putIfAbsent(first, new HashMap<>());
            graph.putIfAbsent(second, new HashMap<>());

            graph.get(first).put(second, minutes);
            graph.get(second).put(first, minutes);
        }

        int minutesOnStop = Integer.parseInt(reader.readLine());

        int start = Integer.parseInt(reader.readLine());
        int destination = Integer.parseInt(reader.readLine());

        for (int node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
            parents.put(node, -1);
        }

        distances.put(start, 0);

        findShortestPath(minutesOnStop, start, destination);

        System.out.println("Total time: " + distances.get(destination));

        List<Integer> path = new ArrayList<>();
        Integer node = destination;
        path.add(node);
        node = parents.get(node);

        while (node != -1) {
            path.add(0, node);
            node = parents.get(node);
        }

        path.stream().forEach(System.out::println);

    }

    private static void findShortestPath(int minutesOnStop, int start, int destination) {
        queue.offer(start);

        while (!queue.isEmpty()) {
            Integer parent = queue.poll();
            visited.add(parent);

            Set<Integer> children = graph.get(parent).keySet();

            for (int child : children) {
                if (!visited.contains(child)) {
                    int newDistance = distances.get(parent) + graph.get(parent).get(child);

                    if (child != destination) {
                        newDistance += minutesOnStop;
                    }

                    int currentDistance = distances.get(child);

                    if (newDistance < currentDistance) {
                        distances.put(child, newDistance);
                        parents.put(child, parent);
                    }

                    queue.offer(child);
                }
            }
        }
    }
}
