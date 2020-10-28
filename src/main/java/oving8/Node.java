package oving8;

public class Node {
    private Edge edge;
    private int nodeNumber;
    private int currentIndex;
    private int dist;
    private boolean isFinished;
    private Node previousNode;
    private static final int infinity = 2147483647>>1;
    private int code;
    private double latitude;
    private double longitude;






    public Node(int nodeNumber){
        this.edge = null;
        this.nodeNumber = nodeNumber;
        this.currentIndex = nodeNumber;
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
    public void  setDistInf(){
        this.dist = infinity;
    }
    public static int getInfinity() {
        return infinity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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