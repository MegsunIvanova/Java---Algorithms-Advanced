package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class P03_BattlePoints {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[] enemies = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] points = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int energy = Integer.parseInt(reader.readLine());

        int[][] dp = new int[enemies.length + 1][energy + 1];

        boolean[][] defeated = new boolean[enemies.length + 1][energy + 1];

        for (int enemyRow = 1; enemyRow <= enemies.length; enemyRow++) {
            int enemyEnergy = enemies[enemyRow - 1];
            int pointsForDefeat = points[enemyRow - 1];

            for (int energyCol = 0; energyCol <= energy; energyCol++) {
                int excluded = dp[enemyRow - 1][energyCol];

                if (energyCol - enemyEnergy < 0) {
                    dp[enemyRow][energyCol] = excluded;
                } else {
                    int included = dp[enemyRow - 1][energyCol - enemyEnergy] + pointsForDefeat;

                    if (excluded > included) {
                        dp[enemyRow][energyCol] = excluded;
                    } else {
                        dp[enemyRow][energyCol] = included;
                        defeated[enemyRow][energyCol] = true;
                    }
                }
            }
        }

        int totalPoints = dp[enemies.length][energy];

        System.out.println(totalPoints);


    }
}
