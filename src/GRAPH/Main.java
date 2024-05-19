package GRAPH;

public class Main {
    public static void main(String[] args) {
        Graph g = new CityGraph();
        g.add_vertex(new Vertex<>(new City("Goslar (Niedersachsen)", 51.54, 10.26), 0));
        g.add_vertex(new Vertex<>(new City("Dettelbach (Bayern)", 49.48, 10.11), 1));
        g.add_vertex(new Vertex<>(new City("Hiroshima (Japan)", 34.23, 132.27), 2));

        g.compute_edges();

        System.out.println(g);
    }
}
