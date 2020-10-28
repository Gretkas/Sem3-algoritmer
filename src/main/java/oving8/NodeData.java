package oving8;

public class NodeData {
    private Node previousNode;
    private int dist;
    private static final int infinity = 2147483647>>1;

    public NodeData() {
        this.dist = infinity;
    }

    public Node getPreviousNode() {
        return previousNode;
    }
    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
    public int getDist() {
        return dist;
    }
    public void setDist(int dist) {
        this.dist = dist;
    }
    public static int getInfinity() {
        return infinity;
    }




}
