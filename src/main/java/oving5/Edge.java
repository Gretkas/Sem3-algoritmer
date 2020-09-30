package oving5;

public class Edge {
    private Node nodeTo;
    private Edge nextEdge;

    public Edge(Node nodeTo, Edge nextEdge) {
        this.nodeTo = nodeTo;
        this.nextEdge = nextEdge;
    }

    public Edge() {
        this.nodeTo = null;
        this.nextEdge = null;
    }

    public Node getNodeTo() {
        return nodeTo;
    }
    public Edge getNextEdge() {
        return nextEdge;
    }
}
