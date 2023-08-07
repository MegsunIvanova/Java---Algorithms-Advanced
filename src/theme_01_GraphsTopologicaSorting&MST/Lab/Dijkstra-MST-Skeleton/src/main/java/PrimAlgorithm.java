import java.util.*;

public class PrimAlgorithm {

    public static Set<Integer> visited = new HashSet<>();
    public static Map<Integer, List<Edge>> edgesByNode = new HashMap<>();

    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {

        List<Edge> forest = new ArrayList<>();

        for (Edge edge : edges) {
            edgesByNode.putIfAbsent(edge.getStartNode(), new ArrayList<>());
            edgesByNode.putIfAbsent(edge.getEndNode(), new ArrayList<>());

            edgesByNode.get(edge.getStartNode()).add(edge);
            edgesByNode.get(edge.getEndNode()).add(edge);

        }

        for (Integer node : edgesByNode.keySet()) {
            if (!visited.contains(node)) {
                prim(node, forest);
            }
        }

        forest.sort(Comparator.comparingInt(Edge::getWeight));

        return forest;
    }

    public static void prim(int start, List<Edge> forest) {

        visited.add(start);

        PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        edges.addAll(edgesByNode.get(start));

        while (!edges.isEmpty()) {
            Edge minEdge = edges.poll();

            int sourceNode = minEdge.getStartNode();
            int destinationNode = minEdge.getEndNode();

            int nonTreeNode = -1;

            if (visited.contains(destinationNode) && !visited.contains(sourceNode)) {
                nonTreeNode = sourceNode;
            }

            if (visited.contains(sourceNode) && !visited.contains(destinationNode)) {
                nonTreeNode = destinationNode;
            }

            if (nonTreeNode != -1) {
                forest.add(minEdge);
                visited.add(nonTreeNode);
                edges.addAll(edgesByNode.get(nonTreeNode));
            }

        }

    }

}
