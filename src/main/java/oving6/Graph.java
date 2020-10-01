package oving6;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Graph {
    private Node[] priorityQueue;
    private int numberNode, numberEdge;



    public void newGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.numberNode = Integer.parseInt(st.nextToken());
        this.priorityQueue = new Node[numberNode];

        for (int i = 0; i < numberNode; i++) {
            priorityQueue[i] = new Node(i);
        }

        this.numberEdge = Integer.parseInt(st.nextToken());

        for (int i = 0; i < numberEdge; i++) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            Edge e = new Edge(priorityQueue[til], priorityQueue[fra].getEdge(),weight);
            priorityQueue[fra].setEdge(e);
        }
    }


    private void initializeNodeData(int startIndex){
        for (int i = 0; i < numberNode; i++) {
            priorityQueue[i].setNodeData(new NodeData());
            priorityQueue[i].setCurrentIndex(priorityQueue[i].getNodeNumber());
        }
        priorityQueue[startIndex].getNodeData().setDist(0);
    }


    /**
     * sets start node of the priorityqueue to the index zero*/
    private void startPriorityQueue(int startIndex){
        initializeNodeData(startIndex);
        swapPQ(0,startIndex);
    }


    public void bubbleDown(int index){
        int swapIndex = (index<<1)+1;
        int right = (index+1)<<1;
        if(swapIndex>=numberNode || right>=numberNode) return;
        int dist = priorityQueue[swapIndex].getNodeData().getDist();

        if(dist > priorityQueue[right].getNodeData().getDist()){
            dist = priorityQueue[right].getNodeData().getDist();
            swapIndex = right;
        }
        if(priorityQueue[swapIndex].isFinished()) return;
        if (priorityQueue[index].getNodeData().getDist() > dist){
            swapPQ(index,swapIndex);
            bubbleDown(swapIndex);
        }
    }

    public void bubbleUp(int index){
        int parentIndex = (index-1)>>1;
        if(parentIndex<0) return;

        if(priorityQueue[index].getNodeData().getDist() < priorityQueue[parentIndex].getNodeData().getDist()){
            swapPQ(index,parentIndex);
            bubbleUp(parentIndex);
        }
    }

    public Node popPriorityQueue(int lastIndex){
        Node n = priorityQueue[0];
        n.setFinished(true);
        swapPQ(0,lastIndex); //test dette
        bubbleDown(0);
        return n;
    }

    private void swapPQ(int i, int j){
        Node n = priorityQueue[i];
        priorityQueue[i].setCurrentIndex(j);
        priorityQueue[j].setCurrentIndex(i);
        priorityQueue[i] = priorityQueue[j];
        priorityQueue[j] = n;
    }

    private void adjustDistance(Node node,Edge edge){
        int newDist = (node.getNodeData().getDist() + edge.getWeight());
        NodeData nodeData = edge.getNodeTo().getNodeData();
        if(newDist < nodeData.getDist()){
            nodeData.setDist(newDist);
            nodeData.setPreviousNode(node);
            bubbleUp(edge.getNodeTo().getCurrentIndex());
        }
    }

    public void dijkstra(int index){
        startPriorityQueue(index);
        for (int i = (numberNode-1); i > 0; i--) {
            Node node = popPriorityQueue(i);
            for (Edge e = node.getEdge();e!=null;e = e.getNextEdge()){
                adjustDistance(node,e);
            }

        }
    }

    public void sortResult(){
        Arrays.sort(priorityQueue, Comparator.comparingInt(Node::getNodeNumber));
    }

























    public Node[] getPriorityQueue() {
        return priorityQueue;
    }
    public void setPriorityQueue(Node[] priorityQueue) {
        this.priorityQueue = priorityQueue;
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
}
