package GRAPH;

public class Edge {
    Vertex<?> from;
    Vertex<?> to;
    double weight;

    public Edge(Vertex<?> from, Vertex<?> to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from + " -> " + to + " (" + weight + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Edge)) return false;
        Edge e = (Edge) obj;
        return from.equals(e.from) && to.equals(e.to) && weight == e.weight;
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }
}
