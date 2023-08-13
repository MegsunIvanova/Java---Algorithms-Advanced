import java.util.*;

public class StronglyConnectedComponents {

    private static List<List<Integer>> stronglyConnectedComponents;
    private static boolean[] visited;
    private static Deque<Integer> stack = new ArrayDeque<>();
    private static List<Integer>[] graph;
    private static List<Integer>[] reversedGraph;

    public static List<List<Integer>> findStronglyConnectedComponents(List<Integer>[] targetGraph) {
        graph = targetGraph;
        visited = new boolean[graph.length];
        stronglyConnectedComponents = new ArrayList<>();

        //For each vertex v in G:
        //  Call DFS(v) to traverse the graph (visit each node once)
        //  Each time DFS(v) finishes (before recursive return), push v onto S
        for (int node = 0; node < graph.length; node++) {
            if (!visited[node]) {
                dfs(node);
            }
        }

        //Build the reverse graph G' (reverse all edges from G)
        setReversedGraph();

        //While S is non-empty:
        //  Pop the top vertex v from S
        //  if v is not visited, call ReverseDFS(v) to find the next stronglyconnected component

        Arrays.fill(visited, false);

        while (!stack.isEmpty()) {
            int node = stack.pop();

            if (!visited[node]) {
                stronglyConnectedComponents.add(new ArrayList<>());
                reversedDfs(node);
            }
        }

        return stronglyConnectedComponents;
    }

    private static void reversedDfs(int node) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        stronglyConnectedComponents.get(stronglyConnectedComponents.size() - 1).add(node);

        for (int child : reversedGraph[node]) {
            reversedDfs(child);
        }

    }

    @SuppressWarnings("unchecked")
    public static void setReversedGraph() {
        reversedGraph = new ArrayList[graph.length];

        for (int i = 0; i < reversedGraph.length; i++) {
            reversedGraph[i] = new ArrayList<>();
        }

        for (int node = 0; node < graph.length; node++) {
            for (int child = 0; child < graph[node].size(); child++) {
                int parent = graph[node].get(child);
                reversedGraph[parent].add(node);
            }
        }
    }

    private static void dfs(int node) {
        if (visited[node]) {
            return;
        }
        visited[node] = true;

        for (int child : graph[node]) {
            dfs(child);
        }

        stack.push(node);
    }
}
