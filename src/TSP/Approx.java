package TSP;

import GRAPH.City;
import GRAPH.Edge;
import GRAPH.EdgeCityGraph;
import GRAPH.Vertex;

import java.util.*;

public class Approx {
    // Method to perform the 2-approximation TSP
    public static List<Edge> twoApproximationTSP(EdgeCityGraph mst, Vertex startNode) {
        List<Vertex> preorderNodes = preorderTraversal(mst, startNode);
        List<Edge> tspTour = new ArrayList<>();

        for (int i = 0; i < preorderNodes.size() - 1; i++) {
            Vertex u = preorderNodes.get(i);
            Vertex v = preorderNodes.get(i + 1);
            tspTour.add(findEdge(mst, u, v));
        }

        // Add edge to return to the starting node
        tspTour.add(findEdge(mst, preorderNodes.get(preorderNodes.size() - 1), startNode));

        return tspTour;
    }

    // Preorder traversal of the MST
    private static List<Vertex> preorderTraversal(EdgeCityGraph graph, Vertex start) {
        List<Vertex> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        preorderHelper(graph, start, visited, result);
        return result;
    }

    // Recursive helper method for preorder traversal
    private static void preorderHelper(EdgeCityGraph graph, Vertex node, Set<Vertex> visited, List<Vertex> result) {
        visited.add(node);
        result.add(node);

        Map<Double, ArrayList<Vertex>> edges = graph.adj_list.get(node);
        for (Map.Entry<Double, ArrayList<Vertex>> entry : edges.entrySet()) {
            for (Vertex neighbor : entry.getValue()) {
                if (!visited.contains(neighbor)) {
                    preorderHelper(graph, neighbor, visited, result);
                }
            }
        }
    }

    // Method to find an edge between two vertices in the graph
    private static Edge findEdge(EdgeCityGraph graph, Vertex u, Vertex v) {
        for (Edge edge : graph.getEdges()) {
            if ((edge.from.equals(u) && edge.to.equals(v)) || (edge.from.equals(v) && edge.to.equals(u))) {
                return edge;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Example usage
        EdgeCityGraph graph = new EdgeCityGraph();

        // Add vertices
        Vertex v0 = new Vertex(new City("City0", 0, 0), 0);
        Vertex v1 = new Vertex(new City("City1", 1, 1), 1);
        Vertex v2 = new Vertex(new City("City2", 2, 2), 2);
        Vertex v3 = new Vertex(new City("City3", 3, 3), 3);

        graph.add_vertex(v0);
        graph.add_vertex(v1);
        graph.add_vertex(v2);
        graph.add_vertex(v3);

        // Create a complete graph by connecting every vertex to every other vertex
        graph.compute();

        // Perform TSP approximation
        List<Edge> tspTour = twoApproximationTSP(graph, v0);

        // Output the edge list
        for (Edge edge : tspTour) {
            System.out.println("Edge: " + edge.from.label + " -> " + edge.to.label + " Weight: " + edge.weight);
        }
    }
}
