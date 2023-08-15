package theme_04_DynamicProgrammingAdvanced.Exercise;

import java.util.Arrays;
import java.util.Scanner;

public class P02_TheMadGardener {
    private static class Sequence {
        private int size;
        private int prev;
        private int sum;

        @Override
        public String toString() {
            return String.format("size %s, prev %s, sum %s", size, prev, sum);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] input = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] plants = new int[input.length + 1]; //{0, 1, 2, 3 ... input.length+1}
        for (int i = 1; i < plants.length; i++) {
            plants[i] = input[i - 1];
        }

        Sequence[] firstLIS = getLIS(plants);

        int[] reversed = new int[plants.length];

        for (int i = 1, j = plants.length - 1; i < reversed.length; i++, j--) {
            reversed[i] = plants[j];
        }

        Sequence[] secondLIS = getLIS(reversed);

        int maxSize = 0;
        int maxSum = 0;
        int peek = 0;

        int end = plants.length - 1;

        for (int i = 1; i < plants.length; i++) {
            int size = firstLIS[i].size + secondLIS[end - i + 1].size;
            if (size >= maxSize) {
                maxSize = size;
                maxSum = firstLIS[i].sum + secondLIS[end - i + 1].sum - plants[i];
                peek = i;
            }
        }

        int[] result = new int[plants.length];

        int element = firstLIS[peek].size;
        int index = peek;
        int nextElement = 0;

        while (index != 0) {
            nextElement++;
            result[element--] = plants[index];
            index = firstLIS[index].prev;
        }

//        element = firstLIS[peek].size + 1;
        index = secondLIS[plants.length - peek].prev;

        while (index != 0) {
            result[++nextElement] = reversed[index];
            index = secondLIS[index].prev;
        }

        for (int i = 1; i < maxSize; i++) {
            System.out.print(result[i] + " ");
        }

        System.out.println();

        System.out.printf("%.2f%n", maxSum / ((maxSize - 1) * 1.00));

        System.out.println(maxSize - 1);

    }

    private static Sequence[] getLIS(int[] plants) {
        Sequence[] LIS = new Sequence[plants.length];

        LIS[0] = new Sequence();

        for (int c = 1; c < plants.length; c++) {
            LIS[c] = new Sequence();
            for (int p = 0; p < c; p++) {
                if (plants[p] <= plants[c]) {
                    if (LIS[p].size + 1 > LIS[c].size
                            || (LIS[c].size + 1 == LIS[p].size && LIS[p].sum + plants[c] > LIS[c].sum)) {

                        LIS[c].size = LIS[p].size + 1;
                        LIS[c].sum = LIS[p].sum + plants[c];
                        LIS[c].prev = p;
                    }
                }
            }
        }

        return LIS;
    }
}
