package GRAPH;

public class CityGraph extends Graph{
    public CityGraph() {
        super();
    }
    @Override
    public void compute_edges() {
        for (Vertex v : adj_list.keySet()) {
            City c1 = (City) v.node;
            for (Vertex v2 : adj_list.keySet()) {
                City c2 = (City) v2.node;
                if (!c1.equals(c2)) {
                    double distance = compute_distance(c1, c2);

                }
            }
        }
    }

    private double compute_distance(City c1, City c2){
        double lat1 = c1.lat;
        double lon1 = c1.lon;
        double lat2 = c2.lat;
        double lon2 = c2.lon;
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }

}
