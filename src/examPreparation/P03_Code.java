package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class P03_Code {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[] first = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int[] second = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        int[][] dp = new int[first.length + 1][second.length + 1];

        for (int rowIndex = 1; rowIndex <= first.length; rowIndex++) {
            for (int colIndex = 1; colIndex <= second.length; colIndex++) {
                if (first[rowIndex - 1] == second[colIndex - 1]) {
                    dp[rowIndex][colIndex] = dp[rowIndex - 1][colIndex - 1] + 1;
                } else {
                    dp[rowIndex][colIndex] = Math.max(dp[rowIndex][colIndex - 1], dp[rowIndex - 1][colIndex]);
                }
            }
        }

        //Print LCS
        List<Integer> result = new ArrayList<>();

        int row = first.length - 1;
        int col = second.length - 1;

        while (row >= 0 && col >= 0) {
            if (first[row] == second[col]) {
                result.add(0, first[row]);
                row--;
                col--;
            } else if (dp[row][col + 1] > dp[row + 1][col]) {
                row--;
            } else {
                col--;
            }
        }

        System.out.println(result.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        System.out.println(dp[first.length][second.length]);


    }
}
