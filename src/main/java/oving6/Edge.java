package oving6;

public class Edge {
    private Node nodeTo;
    private Edge nextEdge;
    private int weight;

    public Edge(Node nodeTo, Edge nextEdge,int weight) {
        this.nodeTo = nodeTo;
        this.nextEdge = nextEdge;
        this.weight = weight;
    }

    public Edge() {
        this.nodeTo = null;
        this.nextEdge = null;
        this.weight = 0;
    }

    public Node getNodeTo() {
        return nodeTo;
    }
    public Edge getNextEdge() {
        return nextEdge;
    }
    public int getWeight(){
        return weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "nodeTo=" + nodeTo +
                ", nextEdge=" + nextEdge +
                ", weight=" + weight +
                '}';
    }
}
