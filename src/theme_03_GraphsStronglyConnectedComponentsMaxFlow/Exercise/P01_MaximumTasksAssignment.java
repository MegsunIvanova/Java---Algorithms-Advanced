package theme_03_GraphsStronglyConnectedComponentsMaxFlow.Exercise;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

public class P01_MaximumTasksAssignment {
    public static boolean[][] graph;
    public static int parents[];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int people = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int tasks = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        int nodes = people + tasks + 2;
        graph = new boolean[nodes][nodes];
        parents = new int[graph.length];

        for (int person = 0; person < people; person++) {
            char[] charArray = scanner.nextLine().toCharArray();
            for (int task = 0; task < tasks; task++) {
                char letter = charArray[task];
                boolean canExecuteTask = letter == 'Y';
                graph[person][people + task] = canExecuteTask;
            }
        }

        int source = nodes - 2;
        int target = nodes - 1;

        for (int person = 0; person < people; person++) {
            graph[source][person] = true;
        }

        for (int task = 0; task < tasks; task++) {
            graph[people + task][target] = true;
        }

        while (bfs(source, target)) {
            int node = target;
            while (node != source) {
                graph[parents[node]][node] = false;
                graph[node][parents[node]] = true;
                node = parents[node];
            }
        }

        for (int person = 0; person < people; person++) {
            for (int task = 0; task < tasks; task++) {
                //From task to person there is Back-edge =>
                // so the task is assigned to that person
                if (graph[people + task][person]) {
                    System.out.printf("%c-%d%n", person + 65, task + 1);
                }
            }
        }

//        printGraph();

    }

    private static boolean bfs(int source, int target) {
        boolean visited[] = new boolean[graph.length];

        Arrays.fill(parents, -1);

        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(source);

        visited[source] = true;

        while (!queue.isEmpty()) {
            Integer node = queue.poll();

            for (int child = graph[node].length - 1; child >= 0; child--) {
                if (graph[node][child] && !visited[child]) {
                    visited[child] = true;
                    parents[child] = node;
                    queue.offer(child);
                }
            }
        }

        return visited[target];
    }

    private static void printGraph() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print((graph[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }
}
