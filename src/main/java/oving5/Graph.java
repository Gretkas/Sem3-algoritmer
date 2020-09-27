package oving5;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Graph {
    private int numberNode, numberEdge;
    private ArrayList<Integer> aL;
    private Node[] nodes;

    public Graph(int numberNode, int numberEdge, Node[] nodes) {
        this.numberNode = numberNode;
        this.numberEdge = numberEdge;
        this.nodes = nodes;
    }

    public Graph() {
        this.numberNode = 0;
        this.numberEdge = 0;
        this.nodes = null;
    }

    public void newGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        this.numberNode = Integer.parseInt(st.nextToken());
        this.nodes = new Node[numberNode];

        for (int i = 0; i < numberNode; i++) {
            nodes[i] = new Node(i);

        }

        this.numberEdge = Integer.parseInt(st.nextToken());

        for (int i = 0; i < numberEdge; i++) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());

            Edge e = new Edge(nodes[til],nodes[fra].getEdge());
            nodes[fra].setEdge(e);
        }


    }



    public ArrayList<ArrayList<Integer>> dfs(){
        dfsInit();
        ArrayList<ArrayList<Integer>> aLaL = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            if(nodes[i].getPrev().getFoundTime() == 0){
                aL = new ArrayList<>();
                nodes[i].getPrev().setDist(0);
                dfsSearch(nodes[i]);
                ArrayList<Integer> allaHuabhbar = new ArrayList<>(aL);
                aLaL.add(allaHuabhbar);
            }
        }
        return aLaL;
    }

    private void dfsInit(){
        for (int i = 0; i < numberNode; i++) {
            nodes[i].setPrev(new Previous());
        }
        Previous.nullTime();
    }

    private void dfsSearch(Node nude){
        aL.add(nude.getNodeNumber());
        Previous p1 = nude.getPrev();
        p1.setFoundTime(Previous.readTime());

        for (Edge e = nude.getEdge();e!=null;e=e.getNextEdge()){
            Previous p2 = e.getNodeTo().getPrev();
            if(p2.getFoundTime() == 0){
                p2.setPrevious(nude);
                p2.setDist(p1.getDist()+1);
                dfsSearch(e.getNodeTo());
            }
        }
        p1.setFinishedTime(Previous.readTime());
    }









    public int getNumberNode() {
        return numberNode;
    }

    public void setNumberNode(int numberNode) {
        this.numberNode = numberNode;
    }

    public int getNumberEdge() {
        return numberEdge;
    }

    public void setNumberEdge(int numberEdge) {
        this.numberEdge = numberEdge;
    }

    public Node[] getNodes() {
        return nodes;
    }

}
