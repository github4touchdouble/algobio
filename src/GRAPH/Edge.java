package GRAPH;

public class Edge {
    public Vertex from;
    public Vertex to;
    public Double weight;

    public Edge(Vertex from, Vertex to, Double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
