package GRAPH;

import java.util.*;

public class EdgeGraph {
    public HashMap<Vertex, HashMap<Double, ArrayList<Vertex>>> adj_list;
    ArrayList<Edge> edges;

    public EdgeGraph() {
        this.adj_list = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    public void add_vertex(Vertex v) {
        adj_list.put(v, new HashMap<>());
    }

    public void remove_vertex(Vertex v) {
        adj_list.values().forEach(e -> e.remove(v));
        adj_list.remove(v);
    }

    public void add_edge(Vertex v1, Vertex v2) {
        ArrayList<Vertex> eV1 = adj_list.get(v1).get(0.0);
        ArrayList<Vertex> eV2 = adj_list.get(v2).get(0.0);
        if (eV1 == null) {
            eV1 = new ArrayList<>();
            adj_list.get(v1).put(0.0, eV1);
        }
        if (eV2 == null) {
            eV2 = new ArrayList<>();
            adj_list.get(v2).put(0.0, eV2);
        }
        eV1.add(v2);
        eV2.add(v1);
    }

    public void remove_edge(Vertex v1, Vertex v2) {
        ArrayList<Vertex> eV1 = adj_list.get(v1).get(0.0);
        ArrayList<Vertex> eV2 = adj_list.get(v2).get(0.0);
        if (eV1 != null) {
            eV1.remove(v2);
        }
        if (eV2 != null) {
            eV2.remove(v1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Vertex, HashMap<Double, ArrayList<Vertex>>> entry : adj_list.entrySet()) {
            Vertex v = entry.getKey();
            sb.append(v).append(" -> ");
            HashMap<Double, ArrayList<Vertex>> edges = entry.getValue();
            for (Map.Entry<Double, ArrayList<Vertex>> entry2 : edges.entrySet()) {
                ArrayList<Vertex> e = entry2.getValue();
                for (Vertex v2 : e) {
                    sb.append(v2).append(", ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void compute_edges() {
        for (Vertex v : adj_list.keySet()) {
            for (Vertex v2 : adj_list.keySet()) {
                if (!v.equals(v2)) {
                    boolean found = false;
                    if (adj_list.get(v2).get(0.0) != null) {
                        for (Vertex x : adj_list.get(v2).get(0.0)) {
                            if (x.equals(v)) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        add_edge(v, v2);
                    }
                }
            }
        }
    }

    public int get_vertex_count() {
        return adj_list.size();
    }

    public int get_edge_count() {
        int count = 0;
        for (Map.Entry<Vertex, HashMap<Double, ArrayList<Vertex>>> entry : adj_list.entrySet()) {
            HashMap<Double, ArrayList<Vertex>> edges = entry.getValue();
            for (Map.Entry<Double, ArrayList<Vertex>> entry2 : edges.entrySet()) {
                count += entry2.getValue().size();
            }
        }
        return count;
    }

    public void depthFirstSearch(Vertex v, StringBuilder path, int[] steps) {
        if (!v.isVisited()) {
            steps[0]++;
            v.setVisited(true); // mark start as visited

            HashMap<Double, ArrayList<Vertex>> edges = adj_list.get(v);
            List<Double> sortedEdges = new ArrayList<>(edges.keySet());
            Collections.sort(sortedEdges);

            for (Double distance : sortedEdges) {
                ArrayList<Vertex> vertices = edges.get(distance);
                for (Vertex w : vertices) {
                    if (!w.isVisited()) { // make sure to visit only unvisited vertices
                        path.append(v.label).append("\t").append(w.label).append("\t").append(distance).append("\n");
                        depthFirstSearch(w, path, steps);
                    }
                }
            }
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}
