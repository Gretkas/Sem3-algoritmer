package oving8;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class GraphAStar{
    private HeapPQ priorityQueue;
    private int numberNode, numberEdge;
    private NodeAStar goalNode;
    private int numberNodesChecked = 0;

    public LinkedList<NodeAStar> findDistance(int startIndex){
        LinkedList<NodeAStar> result = new LinkedList<>();

        //for loop to trace back to the starting node
        for (NodeAStar n = aStar(startIndex);n!=null;n=n.getPreviousNode()){
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
            int nNumber = Integer.parseInt(st.nextToken());
            double lat = Double.parseDouble(st.nextToken());
            double lon = Double.parseDouble(st.nextToken());
            NodeAStar n = new NodeAStar(nNumber,lat,lon);
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

    public int findStartGoalNodes(String start, String slutt, BufferedReader br) throws  IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numbPoints = Integer.parseInt(st.nextToken());
        int startIndex = -1;

        for (int i = 0; i < numbPoints; i++) {
            st = new StringTokenizer(br.readLine());
            int nodeNumb = Integer.parseInt(st.nextToken());
            st.nextToken();
            String name = st.nextToken();
            while(st.hasMoreTokens()){
                name += " " + st.nextToken();
            }
            if(name.equals("\""+start+"\"")){
                startIndex = nodeNumb;
            }
            else if(name.equals("\""+slutt+"\"")){
                this.goalNode = (NodeAStar) priorityQueue.get(nodeNumb);
            }
        }
        return startIndex;
    }

    private void adjustDistance(NodeAStar node, Edge edge){
        int newStartDist = (node.getStartDist() + edge.getWeight());
        NodeAStar nas = (NodeAStar) edge.getNodeTo();
        if(nas.getHeuristicDist() == NodeAStar.infinity){
            nas.calculateHeuristic(goalNode);
        }

        if(newStartDist < nas.getStartDist()){
            nas.setStartDist(newStartDist);
            nas.setPreviousNode(node);
        }
        int newDist = nas.getStartDist() + nas.getHeuristicDist();
        if(newDist < nas.getDist()){
            nas.setDist(newDist);
            priorityQueue.bubbleUpAStar(nas.getCurrentIndex());
        }
    }

    private NodeAStar aStar(int startIndex){
        priorityQueue.startPriorityQueue(startIndex);
        for (int i = (numberNode-1); i > 0; i--) {
            NodeAStar node = (NodeAStar) priorityQueue.pop(i);
            numberNodesChecked++;
            if(node.getNodeNumber() == goalNode.getNodeNumber()) return node;
            for (Edge e = node.getEdge(); e!=null; e = e.getNextEdge()){
                adjustDistance(node,e);
            }
        }
        return null;
    }

    public int getNumberNodesChecked() {
        return numberNodesChecked;
    }
}
