package oving6;

import java.io.BufferedReader;
import java.io.IOException;
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
