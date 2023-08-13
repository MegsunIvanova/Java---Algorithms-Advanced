package theme_04_DynamicProgrammingAdvanced.Lab;

import java.util.*;

public class P01_Knapsack_Iterative {
    private static List<Integer> weights = new ArrayList<>();
    private static List<Integer> prices = new ArrayList<>();

    private static class Item implements Comparable<Item> {
        private String name;
        private int weight;
        private int price;

        public Item(String name, int weight, int price) {
            this.name = name;
            this.weight = weight;
            this.price = price;
        }

        @Override
        public int compareTo(Item other) {
            return this.name.compareTo(other.name);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = Integer.parseInt(scanner.nextLine());

        List<Item> items = new ArrayList<>();

        String line = scanner.nextLine();

        while (!"end".equals(line)) {
            String[] tokens = line.split("\\s+");

            items.add(new Item(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));

            line = scanner.nextLine();
        }

        int[][] dp = new int[items.size() + 1][capacity + 1];
        boolean[][] takenItems = new boolean[items.size() + 1][capacity + 1];

        for (int itemRow = 1; itemRow <= items.size(); itemRow++) {
            Item item = items.get(itemRow - 1);

            for (int capacityCol = 0; capacityCol <= capacity; capacityCol++) {
                int excluded = dp[itemRow - 1][capacityCol];

                if (capacityCol - item.weight < 0) {
                    dp[itemRow][capacityCol] = excluded;
                } else {
                    int included = dp[itemRow - 1][capacityCol - item.weight] + item.price;

                    if (excluded > included) {
                        dp[itemRow][capacityCol] = excluded;
                    } else {
                        dp[itemRow][capacityCol] = included;
                        takenItems[itemRow][capacityCol] = true;
                    }
                }
            }
        }

        int totalValue = dp[items.size()][capacity];

        int totalWeight = capacity;
        while (dp[items.size()][totalWeight - 1] == totalValue) {
            totalWeight--;
        }

        Set<Item> result = new TreeSet<>();

        int lastItem = items.size();

        while (lastItem > 0) {
            if (takenItems[lastItem][capacity]) {
                Item item = items.get(lastItem -1);
                result.add(item);
                capacity -= item.weight;
            }

            lastItem--;
        }

        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Total Value: " + totalValue);

        for (Item item : result) {
            System.out.println(item.name);
        }

    }


}
