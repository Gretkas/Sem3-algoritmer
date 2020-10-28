package oving8;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private HeapPQ priorityQueue;
    private int numberNode, numberEdge;

    public LinkedList<Node> findDistance(int startIndex, int goalIndex){
        LinkedList<Node> result = new LinkedList<>();

        //for loop to trace back to the starting node
        for (Node n = dijkstraDistance(startIndex,goalIndex);n!=null;n=n.getPreviousNode()){
            result.addFirst(n);
        }
        return result;
    }


    public void readNodeFile(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.numberNode = Integer.parseInt(st.nextToken());
        this.priorityQueue = new HeapPQ(numberNode);

        for (int i = 0; i < numberNode; i++) {
            st = new StringTokenizer(br.readLine());
            Node n = new Node(Integer.parseInt(st.nextToken()));
            n.setLatitude(Double.parseDouble(st.nextToken()));
            n.setLongitude(Double.parseDouble(st.nextToken()));
            priorityQueue.add(n,i);
        }
    }

    public void readEdgeFile(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        this.numberEdge = Integer.parseInt(st.nextToken());

        for (int i = 0; i < numberEdge; i++) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            Edge e = new Edge(priorityQueue.get(til), priorityQueue.get(fra).getEdge(),weight);
            priorityQueue.get(fra).setEdge(e);
        }
    }

    public int readNodeCodes(BufferedReader br, String locationName) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numbPoints = Integer.parseInt(st.nextToken());
        int locationNode = -1;

        for (int i = 0; i < numbPoints; i++) {
            st = new StringTokenizer(br.readLine());
            int nodeNumb = Integer.parseInt(st.nextToken());
            int code = Integer.parseInt(st.nextToken());
            priorityQueue.get(nodeNumb).setCode(code);
            if(st.nextToken().equals("\""+locationName+"\"")){
                locationNode = nodeNumb;
            }
        }
        return locationNode;
    }

    public int[] findStartGoalNodes(String start, String slutt, BufferedReader br) throws  IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numbPoints = Integer.parseInt(st.nextToken());
        int[] nodeNumbers = new int[2];

        for (int i = 0; i < numbPoints; i++) {
            st = new StringTokenizer(br.readLine());
            int nodeNumb = Integer.parseInt(st.nextToken());
            st.nextToken();
            String name = st.nextToken();
            if(name.equals("\""+start+"\"")){
                nodeNumbers[0] = nodeNumb;
            }
            else if(name.equals("\""+slutt+"\"")){
                nodeNumbers[1] = nodeNumb;
            }
        }
        return nodeNumbers;
    }






    private void adjustDistance(Node node, Edge edge){
        int newDist = (node.getDist() + edge.getWeight());
        if(newDist < edge.getNodeTo().getDist()){
            edge.getNodeTo().setDist(newDist);
            edge.getNodeTo().setPreviousNode(node);
            priorityQueue.bubbleUp(edge.getNodeTo().getCurrentIndex());
        }
    }


    private Node dijkstraDistance(int startIndex,int goalIndex){
        priorityQueue.startPriorityQueue(startIndex);
        for (int i = (numberNode-1); i > 0; i--) {
            Node node = priorityQueue.pop(i);
            if(node.getNodeNumber() == goalIndex) return node;
            for (Edge e = node.getEdge(); e!=null; e = e.getNextEdge()){
                adjustDistance(node,e);
            }
        }
        return null;
    }

    public Node[] dijkstraGas(int startIndex,int typeOfRecharge){     //her starter vi!!
        Node[] result = new Node[10];
        int numbersFound = 0;
        priorityQueue.startPriorityQueue(startIndex);
        for (int i = (numberNode-1); i > 0; i--) {
            Node node = priorityQueue.pop(i);
            if (node.getCode() == typeOfRecharge || node.getCode() == 6){
                result[numbersFound] = node;
                numbersFound++;
                if(numbersFound > 9) return result;
            }
            for (Edge e = node.getEdge(); e!=null; e = e.getNextEdge()){
                adjustDistance(node,e);
            }

        }
        return (result[0]==null?null:result);
    }

}
