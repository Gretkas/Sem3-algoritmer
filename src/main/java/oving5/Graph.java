package oving5;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class Graph {
    private int numberNode, numberEdge;
    private ArrayList<Integer> component;
    private Node[] nodes;

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



    public void dfs(){
        dfsInit();
        for (int i = 0; i < nodes.length; i++) {
            if(nodes[i].getNodeData().getFoundTime() == 0){
                dfsSearchStack(nodes[i],false);
            }
        }
    }

    private void dfsInit(){
        for (int i = 0; i < numberNode; i++) {
            nodes[i].setNodeData(new NodeData());
        }
        NodeData.nullTime();
    }



    private void dfsSearchStack(Node node, boolean collectData){
        Stack<Node> stack = new Stack<>();
        node.getNodeData().setFoundTime(NodeData.readTime());
        stack.add(node);
        if(collectData) component.add(node.getNodeNumber());

        while (!stack.isEmpty()) {
            Node n = stack.pop();

            if(n.isFinished()){
                n.getNodeData().setFinishedTime(NodeData.readTime());
            }
            else{
                n.setFinished(true);
                stack.add(n);
                for (Edge e = n.getEdge();e!=null;e=e.getNextEdge()){
                    NodeData p2 = e.getNodeTo().getNodeData();
                    if(p2.getFoundTime() == 0){
                        p2.setFoundTime(NodeData.readTime());
                        if(collectData) component.add(e.getNodeTo().getNodeNumber());
                        p2.setPreviousNode(n);
                        stack.add(e.getNodeTo());
                    }
                }
            }
        }
    }



    public Node[] sortNodes(){
        Arrays.sort(nodes, (o1, o2) -> o2.getNodeData().getFinishedTime()-o1.getNodeData().getFinishedTime());
        return nodes;
    }


    public void newFlippedGraph(BufferedReader br) throws IOException {
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

            Edge e = new Edge(nodes[fra],nodes[til].getEdge());
            nodes[til].setEdge(e);
        }
    }

    public ArrayList<ArrayList<Integer>> dfsReverse(Node[] orderedNodes){
        dfsInit();
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            int cNN = orderedNodes[i].getNodeNumber();
            dfsHelper(components, cNN);
        }
        return components;
    }

    private void dfsHelper(ArrayList<ArrayList<Integer>> components, int cNN) {
        if(nodes[cNN].getNodeData().getFoundTime() == 0){
            component = new ArrayList<>();
            dfsSearchStack(nodes[cNN],true);
            ArrayList<Integer> componentCopy = new ArrayList<>(component);
            components.add(componentCopy);
        }
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
