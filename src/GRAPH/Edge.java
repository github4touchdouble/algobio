package GRAPH;

public class Edge {
    Vertex from;
    Vertex to;
    Double weight;

    public Edge(Vertex from, Vertex to, Double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
