package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P03_EShop {
    private static class Component {
        List<Integer> items;
        int weight;
        int value;

        public Component(java.util.List<Integer> items, int weight, int value) {
            this.items = items;
            this.weight = weight;
            this.value = value;
        }
    }

    private static String[] itemNames;
    private static int[] weights;
    private static int[] values;
    private static boolean[] visited;
    private static boolean[][] itemsGraph;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());

        itemNames = new String[nodes];
        weights = new int[nodes];
        values = new int[nodes];

        Map<String, Integer> itemsIndices = new HashMap<>();

        for (int i = 0; i < nodes; i++) {
            String[] input = reader.readLine().split("\\s+");

            itemsIndices.put(input[0], i);
            itemNames[i] = input[0];
            weights[i] = Integer.parseInt(input[1]);
            values[i] = Integer.parseInt(input[2]);
        }

        itemsGraph = new boolean[nodes][nodes];

        visited = new boolean[nodes];

        int pairs = Integer.parseInt(reader.readLine());

        while (pairs-- > 0) {
            String[] input = reader.readLine().split("\\s+");
            int row = itemsIndices.get(input[0]);
            int col = itemsIndices.get(input[1]);

            itemsGraph[row][col] = true;
            itemsGraph[col][row] = true;
        }

        int capacity = Integer.parseInt(reader.readLine());

        List<Component> components = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            if (!visited[i]) {
                Component connectedItems = bfs(i);
                components.add(connectedItems);
            }
        }

        //knapsack
        int[][] dp = new int[components.size() + 1][capacity + 1];
        boolean[][] takenComponents = new boolean[components.size() + 1][capacity + 1];

        for (int componentRow = 1; componentRow <= components.size(); componentRow++) {
            int componentWeight = components.get(componentRow - 1).weight;
            int componentValue = components.get(componentRow - 1).value;

            for (int capacityCol = 1; capacityCol <= capacity; capacityCol++) {
                int excluded = dp[componentRow - 1][capacityCol];

                if (capacityCol - componentWeight < 0) {
                    dp[componentRow][capacityCol] = excluded;
                } else {
                    int included = dp[componentRow - 1][capacityCol - componentWeight] + componentValue;

                    if (excluded > included) {
                        dp[componentRow][capacityCol] = excluded;
                    } else {
                        dp[componentRow][capacityCol] = included;
                        takenComponents[componentRow][capacityCol] = true;
                    }
                }
            }

        }

        Set<String> result = new TreeSet<>(Comparator.comparingInt(itemsIndices::get));

        int lastComponent = components.size();

        while (lastComponent > 0) {
            if (takenComponents[lastComponent][capacity]) {
                Component component = components.get(lastComponent - 1);
                component.items.stream().map(c -> itemNames[c]).forEach(result::add);
                capacity -= component.weight;
            }

            lastComponent--;
        }

        System.out.println(String.join(System.lineSeparator(), result));
    }

    private static Component bfs(int i) {
        List<Integer> items = new ArrayList<>();
        int totalWeight = 0;
        int totalValue = 0;

        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(i);

        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            visited[node] = true;
            for (int child = 0; child < itemsGraph[node].length; child++) {
                if (itemsGraph[node][child] && !visited[child]) {
                    queue.add(child);
                    visited[child] = true;
                }
            }

            items.add(node);
            totalWeight += weights[node];
            totalValue += values[node];

        }

        return new Component(items, totalWeight, totalValue);
    }
}
