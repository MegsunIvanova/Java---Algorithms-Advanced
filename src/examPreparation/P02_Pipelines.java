package examPreparation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P02_Pipelines {
    private static boolean[][] graph;
    private static int[] parents;

    private static class Edge {
        String from;
        String to;

        public Edge(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return String.format("%s - %s", this.from, this.to);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int countAgents = Integer.parseInt(reader.readLine());
        int countPipelines = Integer.parseInt(reader.readLine());

        Map<String, Integer> agents = new HashMap<>();
        Map<String, Integer> pipelines = new HashMap<>();

        int nodes = countAgents + countPipelines + 2;
        graph = new boolean[nodes][nodes];
        int source = nodes - 2;
        int target = nodes - 1;

        parents = new int[graph.length];


        for (int i = 0; i < countAgents; i++) {
            agents.put(reader.readLine(), i);
            graph[source][i] = true;
        }

        for (int i = countAgents; i < countAgents + countPipelines; i++) {
            pipelines.put(reader.readLine(), i);
            graph[i][target] = true;
        }

        for (int i = 0; i < countAgents; i++) {
            String[] tokens = reader.readLine().split(", ");
            int row = agents.get(tokens[0]);
            for (int j = 1; j < tokens.length; j++) {
                String pipeline = tokens[j];
                int col = pipelines.get(tokens[j]);
                graph[row][col] = true;
            }
        }

        while (bfs(source, target)) {
            int node = target;
            while (node != source) {
                graph[parents[node]][node] = false;
                graph[node][parents[node]] = true;
                node = parents[node];
            }
        }

        for (String agent : agents.keySet()) {
            int agentIndex = agents.get(agent);
            for (String pipeline : pipelines.keySet()) {
                int pipelineIndex = pipelines.get(pipeline);
                //From pipeline to agent there is Back-edge =>
                // so the pipeline is assigned to that agent

                if (graph[pipelineIndex][agentIndex]) {
                    System.out.println(agent + " - " + pipeline);
                }
            }
        }
    }

    private static boolean bfs(int source, int target) {
        boolean visited[] = new boolean[graph.length];

        Arrays.fill(parents, -1);

        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(source);

        visited[source] = true;

        while (!queue.isEmpty()) {
            Integer node = queue.poll();

            for (int child = 0; child < graph[node].length; child++) {
                if (graph[node][child] && !visited[child]) {
                    visited[child] = true;
                    parents[child] = node;
                    queue.offer(child);
                }
            }
        }

        return visited[target];
    }
}
