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

        // Schritt 3: Kanten sortieren
        Collections.sort(sortedEdges, Comparator.comparingDouble(Edge::getWeight));

        while (!sortedEdges.isEmpty()) {
            // Schritt 5: Die Kante mit dem kleinsten Gewicht auswählen
            Edge minEdge = sortedEdges.remove(0);

            // Schritt 7: Prüfen, ob das Hinzufügen der Kante einen Kreis erzeugt
            if (!createsCycle(mstEdges, minEdge)) {
                // Schritt 8: Kante zum minimalen Spannbaum hinzufügen
                mstEdges.add(minEdge);
            }
        }

        return mstEdges;
    }

    private static boolean createsCycle(Set<Edge> mstEdges, Edge edge) {
        // Wir überprüfen, ob die Hinzufügung der Kante einen Kreis im minimalen Spannbaum erzeugt
        // Dies kann geschehen, wenn die beiden Endpunkte der Kante bereits miteinander verbunden sind
        return isConnected(mstEdges, edge.from, edge.to);
    }

    private static boolean isConnected(Set<Edge> mstEdges, Vertex source, Vertex destination) {
        // Wir überprüfen, ob es einen Pfad zwischen den beiden Endpunkten im minimalen Spannbaum gibt
        // Dies kann mit einer Tiefensuche (DFS) oder einer Breitensuche (BFS) durchgeführt werden
        // Hier verwenden wir eine einfache rekursive Tiefensuche
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
                // Schreibe die Kante im Edge-List-Format
                writer.write(edge.from.label + " " + edge.to.label + " " + edge.weight + "\n");
            }
        }
    }
}
