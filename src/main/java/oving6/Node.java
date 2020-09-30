package oving6;

public class Node {
    private Edge edge;
    private int nodeNumber;
    private NodeData nodeData;

    public Node(Edge edge, int nodeNumber) {
        this.edge = edge;
        this.nodeNumber = nodeNumber;
    }

    public Node(int nodeNumber){
        this.edge = null;
        this.nodeNumber = nodeNumber;
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