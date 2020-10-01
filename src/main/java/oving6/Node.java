package oving6;

public class Node {
    private Edge edge;
    private int nodeNumber, currentIndex;
    private NodeData nodeData;
    private boolean isFinished;

    public Node(Edge edge, int nodeNumber) {
        this.edge = edge;
        this.nodeNumber = nodeNumber;
    }

    public Node(int nodeNumber){
        this.edge = null;
        this.nodeNumber = nodeNumber;
    }


    public boolean isFinished() {
        return isFinished;
    }
    public void setFinished(boolean finished) {
        isFinished = finished;
    }
    public int getCurrentIndex() {
        return currentIndex;
    }
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    public NodeData getNodeData() {
        return nodeData;
    }
    public void setNodeData(NodeData nodeData) {
        this.nodeData = nodeData;
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
    @Override
    public String toString() {
        return "Node{" +
                "edge=" + edge +
                ", nodeNumber=" + nodeNumber +
                ", nodeData=" + nodeData +
                '}';
    }
}