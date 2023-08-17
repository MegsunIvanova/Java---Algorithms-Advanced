package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class P01_BestTeam {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[] soldiers = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        int[] longestIncr = new int[soldiers.length];
        int[] longestDecr = new int[soldiers.length];
        int[] prevIncr = new int[soldiers.length];
        int[] prevDecr = new int[soldiers.length];
        Arrays.fill(prevIncr, -1);
        Arrays.fill(prevDecr, -1);

        int lisLastIndex = -1;
        int maxLis = 0;

        int ldsLastIndex = -1;
        int maxLds = 0;

        for (int i = 0; i < soldiers.length; i++) {
            int soldier = soldiers[i];
            longestIncr[i] = 1;
            longestDecr[i] = 1;

            for (int j = 0; j < i; j++) {
                if (soldier >= soldiers[j]) {
                    if (longestIncr[i] < longestIncr[j] + 1) {
                        longestIncr[i] = longestIncr[j] + 1;
                        prevIncr[i] = j;
                    }
                } else {
                    if (longestDecr[i] <= longestDecr[j] + 1) {
                        longestDecr[i] = longestDecr[j] + 1;
                        prevDecr[i] = j;
                    }
                }

                if (maxLis < longestIncr[i]) {
                    maxLis = longestIncr[i];
                    lisLastIndex = i;
                }

                if (maxLds < longestDecr[i]) {
                    maxLds = longestDecr[i];
                    ldsLastIndex = i;
                }
            }
        }

        List<Integer> bestTeam;

        if (ldsLastIndex == lisLastIndex && ldsLastIndex == -1) {
            bestTeam = new ArrayList<>();
            bestTeam.add(soldiers[0]);
        } else if (maxLis > maxLds) {
            bestTeam = getBestTeam(lisLastIndex, prevIncr, soldiers);
        } else {
            bestTeam = getBestTeam(ldsLastIndex, prevDecr, soldiers);
        }

        System.out.println(bestTeam.stream().map(String::valueOf).collect(Collectors.joining(" ")));

    }

    private static List<Integer> getBestTeam(int index, int[] prevIncr, int[] soldiers) {
        List<Integer> bestTeam = new ArrayList<>();
        while (index != -1) {
            bestTeam.add(0, soldiers[index]);
            index = prevIncr[index];
        }

        return bestTeam;
    }
}
