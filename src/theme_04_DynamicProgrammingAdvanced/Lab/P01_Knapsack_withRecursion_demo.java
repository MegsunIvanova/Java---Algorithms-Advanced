package theme_04_DynamicProgrammingAdvanced.Lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class P01_Knapsack_withRecursion_demo {

    private static List<Integer> weights = new ArrayList<>();
    private static List<Integer> prices = new ArrayList<>();
    private static int[][] memo;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = Integer.parseInt(scanner.nextLine());

        String line = scanner.nextLine();

        while (!"end".equals(line)) {
            String[] tokens = line.split("\\s+");
            weights.add(Integer.parseInt(tokens[1]));
            prices.add(Integer.parseInt(tokens[2]));

            line = scanner.nextLine();
        }

        memo = new int[prices.size() + 1][capacity + 1];
        for (int i = 0; i < memo.length; i++) {
            Arrays.fill(memo[i], -1);
        }

        int totalValue = recurrence(0, 0, capacity);

        System.out.println("Total Value: " + totalValue);

    }

    private static int recurrence(int valueIndex, int weightIndex, int capacity) {

        if (valueIndex >= prices.size() || weightIndex >= weights.size()) {
            return 0;
        }

        if (weights.get(weightIndex) > capacity) {
            return 0;
        }

        if (memo[valueIndex][capacity] != -1) {
            return memo[valueIndex][capacity];
        }


        return memo[valueIndex][capacity] = Math.max(recurrence(valueIndex + 1, weightIndex + 1, capacity),
                recurrence(valueIndex + 1, weightIndex + 1, capacity - weights.get(weightIndex))
                        + prices.get(valueIndex));
    }
}
