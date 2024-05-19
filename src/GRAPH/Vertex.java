package GRAPH;

public class Vertex<A> {
    A node;
    public Integer label;
    public Vertex(A node, int label) {
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
        Vertex<?> v = (Vertex<?>) obj;
        if (obj instanceof Integer){
            return label == v.label;
        }
        return node.equals(v.node);


    }
    @Override
    public int hashCode() {
        return node.hashCode();
    }
}
