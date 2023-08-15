package theme_04_DynamicProgrammingAdvanced.Exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P03_ZigzagMatrix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = Integer.parseInt(scanner.nextLine());
        int cols = Integer.parseInt(scanner.nextLine());

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = Arrays.stream(scanner.nextLine().split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        int distances[][] = new int[rows][cols];

        int[][] maxDp = new int[rows][cols];
        int[][] prev = new int[rows][cols];

        for (int[] arr : prev) {
            Arrays.fill(arr, -1);
        }

        for (int row = 0; row < rows; row++) {
            maxDp[row][0] = matrix[row][0];
        }

        for (int col = 1; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int prevMax = 0;

                if (col % 2 != 0) {
                    for (int i = row + 1; i < rows; i++) {
                        if (maxDp[i][col - 1] > prevMax) {
                            prevMax = maxDp[i][col - 1];
                            prev[row][col] = i;
                        }
                    }

                } else {
                    for (int i = 0; i < row; i++) {
                        if (maxDp[i][col - 1] > prevMax) {
                            prevMax = maxDp[i][col - 1];
                            prev[row][col] = i;
                        }
                    }
                }

                maxDp[row][col] = prevMax + matrix[row][col];
            }
        }

        List<Integer> result = new ArrayList<>();

        int colIndex = cols - 1;
        int rowIndex = 0;

        int max = -1;

        for (int row = 0; row < maxDp.length; row++) {
            if (maxDp[row][colIndex] > max) {
                rowIndex = row;
                max = maxDp[row][colIndex];
            }
        }

        while (colIndex >= 0) {
            result.add(0, matrix[rowIndex][colIndex]);
            rowIndex = prev[rowIndex][colIndex];
            colIndex--;
        }

        int sum = result.stream().mapToInt(Integer::intValue).sum();

        String elements = result.stream().map(String::valueOf).collect(Collectors.joining(" + "));

        System.out.println(sum + " = " + elements);
    }
}
