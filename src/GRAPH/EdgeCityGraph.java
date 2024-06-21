package GRAPH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EdgeCityGraph extends Graph {
    public EdgeCityGraph() {
        super();
    }

    @Override
    public void add_edge(Vertex v1, Vertex v2) {
        City c1 = (City) v1.node;
        City c2 = (City) v2.node;
        double distance = compute_distance(c1, c2);

        if (!adj_list.containsKey(v1)) {
            adj_list.put(v1, new HashMap<>());
        }
        if (!adj_list.containsKey(v2)) {
            adj_list.put(v2, new HashMap<>());
        }

        adj_list.get(v1).computeIfAbsent(distance, k -> new ArrayList<>()).add(v2);
        adj_list.get(v2).computeIfAbsent(distance, k -> new ArrayList<>()).add(v1);

        edges.add(new Edge(v1, v2, distance));
    }

    private double compute_distance(City c1, City c2) {
        double lat1 = c1.lat;
        double lon1 = c1.lon;
        double lat2 = c2.lat;
        double lon2 = c2.lon;
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }

    public void compute() {
        for (Vertex v1 : adj_list.keySet()) {
            for (Vertex v2 : adj_list.keySet()) {
                if (!v1.equals(v2)) {
                    add_edge(v1, v2);
                }
            }
        }
    }
}
