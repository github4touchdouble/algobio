package GRAPH;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class KruskalAlgorithm {
    public static Set<Edge> kruskal(Graph graph) {
        Set<Edge> mstEdges = new HashSet<>();
        Set<Edge> edges = new HashSet<>(graph.getEdges());
        List<Edge> sortedEdges = new ArrayList<>(edges);

        // sort edges by weight O(|E| * log(|E|)) (Collections.sort sollte bubblesort sein)
        Collections.sort(sortedEdges, Comparator.comparingDouble(Edge::getWeight));

        while (!sortedEdges.isEmpty()) {
            // get edge with smallest weight
            Edge minEdge = sortedEdges.remove(0);

            // are we in a cycle?
            if (!createsCycle(mstEdges, minEdge)) {
                // add enge to mst
                mstEdges.add(minEdge);
            }
        }

        return mstEdges;
    }

    private static boolean createsCycle(Set<Edge> mstEdges, Edge edge) {
        return isConnected(mstEdges, edge.from, edge.to);
    }

    private static boolean isConnected(Set<Edge> mstEdges, Vertex source, Vertex destination) {
        Set<Vertex> visited = new HashSet<>();
        return dfs(mstEdges, visited, source, destination);
    }

    private static boolean dfs(Set<Edge> mstEdges, Set<Vertex> visited, Vertex current, Vertex destination) {
        if (current.equals(destination)) {
            return true;
        }
        visited.add(current);
        for (Edge edge : mstEdges) {
            if (edge.from.equals(current) && !visited.contains(edge.to)) {
                if (dfs(mstEdges, visited, edge.to, destination)) {
                    return true;
                }
            } else if (edge.to.equals(current) && !visited.contains(edge.from)) {
                if (dfs(mstEdges, visited, edge.from, destination)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void writeMSTToFile(Set<Edge> mstEdges, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(mstEdges.size() + "\n");
            for (Edge edge : mstEdges) {
                writer.write(edge.from.label + " " + edge.to.label + " " + edge.weight + "\n");
            }
        }
    }
}
