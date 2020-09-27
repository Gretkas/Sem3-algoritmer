package oving5;

public class Node {
    private Edge edge;
    private int nodeNumber;
    private Previous prev;

    public Node(Edge edge, int nodeNumber) {
        this.edge = edge;
        this.nodeNumber = nodeNumber;
    }

    public Node(int nodeNumber){
        this.edge = null;
        this.nodeNumber = nodeNumber+1;
    }



    public Previous getPrev() {
        return prev;
    }
    public void setPrev(Previous prev) {
        this.prev = prev;
    }
    public Edge getEdge() {
        return edge;
    }
    public void setEdge(Edge edge) {
        this.edge = edge;
    }
    public int getNodeNumber() {
        return nodeNumber;
    }
    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
}