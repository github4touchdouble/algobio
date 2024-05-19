package GRAPH;

import java.util.ArrayList;

public class CityGraph extends Graph{
    private boolean debug = false;
    private boolean round = false;
    public CityGraph() {
        super();
    }
    public CityGraph(boolean debug, boolean round) {
        super();
        this.debug = debug;
        this.round = round;
    }
    @Override
    public void add_edge(Vertex v1, Vertex v2) {
        City c1 = (City) v1.node;
        City c2 = (City) v2.node;
        double distance = compute_distance(c1, c2, round);
        if (super.adj_list.get(v1).get(distance) == null) {
            super.adj_list.get(v1).put(distance, new ArrayList<>());
            super.adj_list.get(v1).get(distance).add(v2);
        } else {
            if (!super.adj_list.get(v1).get(distance).contains(v2)){
                super.adj_list.get(v1).get(distance).add(v2);
            }
        }
    }

    @Override
    public void compute_edges() {
        for (Vertex v1 : super.adj_list.keySet()) {
            for (Vertex v2 : super.adj_list.keySet()) {
                if (!v1.label.equals(v2.label)) {
                    if (debug) {
                        System.out.println("Adding edge between " + v1.node + " and " + v2.node);
                    }
                    add_edge(v1, v2);
                }
                else {
                    if (debug) {
                        System.out.println("Same city: " + v1.node + " and " + v2.node + " (skipping)");
                    }
                }
            }
        }
    }

    private double compute_distance(City c1, City c2, boolean round){
        double lat1 = c1.lat;
        double lon1 = c1.lon;
        double lat2 = c2.lat;
        double lon2 = c2.lon;
        double unrounded_distance = Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
        if (!round) {
            return unrounded_distance;
        }
        return Double.parseDouble(String.format("%.5g%n",unrounded_distance).replace(",","."));
    }

}
