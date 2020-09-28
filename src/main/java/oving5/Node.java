package oving5;

public class Node {
    private Edge edge;
    private int nodeNumber;
    private NodeData nodeData;
    private boolean finished;

    public Node(Edge edge, int nodeNumber) {
        this.edge = edge;
        this.nodeNumber = nodeNumber;
        this.finished = false;
    }

    public Node(int nodeNumber){
        this.edge = null;
        this.nodeNumber = nodeNumber;
        this.finished = false;
    }



    public NodeData getNodeData() {
        return nodeData;
    }
    public void setNodeData(NodeData prev) {
        this.nodeData = prev;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}