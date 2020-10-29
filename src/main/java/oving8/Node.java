package oving8;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class Node {
    private final int nodeNumber;
    protected static final int infinity = 2147483647>>1;
    private final double latitude;
    private final double longitude;

    private Node previousNode;
    private Edge edge;
    private int currentIndex;
    private int dist;
    private boolean isFinished;
    private int code;

    public Node(int nodeNumber,double latitude,double longitude){
        this.edge = null;
        this.nodeNumber = nodeNumber;
        this.currentIndex = nodeNumber;
        this.latitude = latitude;
        this.longitude = longitude;
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
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
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
}