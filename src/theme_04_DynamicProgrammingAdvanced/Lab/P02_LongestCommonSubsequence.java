package theme_04_DynamicProgrammingAdvanced.Lab;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class P02_LongestCommonSubsequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String first = scanner.nextLine();
        String second = scanner.nextLine();

        int[][] dp = new int[first.length() + 1][second.length() + 1];

        for (int rowIndex = 1; rowIndex <= first.length(); rowIndex++) {
            for (int colIndex = 1; colIndex <= second.length(); colIndex++) {

                if (first.charAt(rowIndex - 1) == second.charAt(colIndex - 1)) {
                    dp[rowIndex][colIndex] = dp[rowIndex - 1][colIndex - 1] + 1;
                } else {
                    dp[rowIndex][colIndex] = Math.max(dp[rowIndex][colIndex - 1], dp[rowIndex - 1][colIndex]);
                }

            }
        }

        System.out.println(dp[first.length()][second.length()]);


        //Print LCS
        Deque<Character> result = new ArrayDeque<>();

        int row = first.length() - 1;
        int col = second.length() - 1;

        while (row >= 0 && col >= 0) {
            if (first.charAt(row) == second.charAt(col)) {
                result.push(first.charAt(row));
                row--;
                col--;
            } else if (dp[row][col + 1] > dp[row + 1][col]) {
                row--;
            } else {
                col--;
            }
        }

        while (!result.isEmpty()) {
            System.out.print(result.pop());
        }
    }
}
