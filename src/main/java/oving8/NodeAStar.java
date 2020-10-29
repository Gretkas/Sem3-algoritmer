package oving8;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class NodeAStar extends Node{
    private final double latitudeRad;
    private final double latitudeCos;
    private final double longitudeRad;

    private NodeAStar previousNode;
    private int heuristicDist;
    private int startDist;

    public NodeAStar(int nodeNumber,double latitude,double longitude){
        super(nodeNumber,latitude,longitude);
        this.heuristicDist = infinity;
        this.startDist = infinity;
        this.latitudeRad = latitude*Math.PI/180;
        this.longitudeRad = longitude*Math.PI/180;
        this.latitudeCos = Math.cos(latitudeRad);
    }


    public void calculateHeuristic (NodeAStar goalNode) {
        double sin_bredde = Math.sin((this.latitudeRad-goalNode.latitudeRad)/2.0);
        double sin_lengde = Math.sin((this.longitudeRad-goalNode.longitudeRad)/2.0);
        this.heuristicDist = (int) (35285538.46153846153846153846*Math.asin(Math.sqrt(sin_bredde*sin_bredde+this.latitudeCos*goalNode.latitudeCos*sin_lengde*sin_lengde)));
    }


    @Override
    public NodeAStar getPreviousNode() {
        return previousNode;
    }
    public void setPreviousNode(NodeAStar previousNode) {
        this.previousNode = previousNode;
    }
    public int getHeuristicDist() {
        return heuristicDist;
    }
    public int getStartDist() {
        return startDist;
    }
    public void setStartDist(int startDist) {
        this.startDist = startDist;
    }
}