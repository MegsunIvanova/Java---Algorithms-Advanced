package theme_04_DynamicProgrammingAdvanced.Exercise;

import java.util.Scanner;

public class P01_AbaspaBasapa_v01 {

    private static int[][] dp;
    private static String first;
    private static String second;


    public static void main(String[] args) {
        //You are given 2 strings. Find the left-most longest ordered common subsequence in the first string
        //(elements should be next to each other in both strings).

        Scanner scanner = new Scanner(System.in);

        first = scanner.nextLine();
        second = scanner.nextLine();

        dp = new int[first.length()][second.length()];
        int[] maxPrevIndices = new int[2];
        int maxLength = 0;

        for (int rowIndex = 0; rowIndex < first.length(); rowIndex++) {
            for (int colIndex = 0; colIndex < second.length(); colIndex++) {
                if (first.charAt(rowIndex) == second.charAt(colIndex)) {
                    int prev = 0;
                    if (isInBounds(rowIndex - 1, colIndex - 1)) {
                        prev = dp[rowIndex - 1][colIndex - 1];
                    }
                    dp[rowIndex][colIndex] = 1 + prev;
                    if (dp[rowIndex][colIndex] > maxLength) {
                        maxPrevIndices[0] = rowIndex;
                        maxPrevIndices[1] = colIndex;
                        maxLength = dp[rowIndex][colIndex];
                    }

                }
            }
        }

        StringBuilder builder = new StringBuilder();
        int r = maxPrevIndices[0];
        int c = maxPrevIndices[1];

        while (maxLength > 0) {
            builder.append(first.charAt(r));
            r--;
            c--;
            maxLength--;
        }

        System.out.println(builder.reverse());
    }

    private static boolean isInBounds(int r, int c) {
        return r >= 0 && r < first.length() && c >= 0 && c < second.length();
    }
}
