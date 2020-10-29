package oving8;

public class EdgeAStar {
    private NodeAStar nodeTo;
    private EdgeAStar nextEdge;
    private int weight;

    public EdgeAStar(NodeAStar nodeTo, EdgeAStar nextEdge, int weight) {
        this.nodeTo = nodeTo;
        this.nextEdge = nextEdge;
        this.weight = weight;
    }

    public EdgeAStar() {
        this.nodeTo = null;
        this.nextEdge = null;
        this.weight = 0;
    }

    public NodeAStar getNodeTo() {
        return nodeTo;
    }
    public EdgeAStar getNextEdge() {
        return nextEdge;
    }
    public int getWeight(){
        return weight;
    }
}
