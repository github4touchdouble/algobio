package GRAPH;

public class Vertex<A> {
    A node;
    public String label;
    public Vertex(A node, String label) {
        this.node = node;
        this.label = label;
    }
    @Override
    public String toString() {
        return node.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Vertex)) return false;
        if (obj instanceof String) {
            return label.equals(obj);
        }
        return false;

    }
    @Override
    public int hashCode() {
        return node.hashCode();
    }
}
