package theme_04_DynamicProgrammingAdvanced.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class P04_RectangleIntersection {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        int[][] matrix = new int[2001][2001]; //All coordinates are integers in the range [-1000, 1000]

        int result = 0;

        for (int i = 0; i < n; i++) {
            int[] coordinates = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int minX = coordinates[0] + 1000;
            int maxX = coordinates[1] + 1000;
            int minY = coordinates[2] + 1000;
            int maxY = coordinates[3] + 1000;

            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    matrix[x][y] += 1;

                    if (matrix[x][y] == 2) {
                        result ++;
                    }
                }
            }
        }

        System.out.println(result);
    }
}
