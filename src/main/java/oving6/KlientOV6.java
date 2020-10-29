package oving6;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class KlientOV6 {
    public static void main(String[] args) throws IOException {
        int startNode = 0;
        String graphStr = "vgSkandinavia";
        String path = "C:\\Users\\robvo\\Desktop\\resources\\oving6\\" + graphStr + ".txt";
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        Graph g = new Graph();
        g.newGraph(br);


        g.dijkstra(startNode);
        g.sortResult();
        System.out.println("Graph: " + graphStr + " start Node: " + startNode);
        for (int i = 0; i < g.getNumberNode(); i++) {
            Node n = g.getPriorityQueue()[i];
            String tidligereNode = "";
            String distanse = " ";
            if(n.getNodeData().getPreviousNode() == null){
                tidligereNode = "unåelig";
            }
            else{
                tidligereNode = String.valueOf(n.getNodeData().getPreviousNode().getNodeNumber());
                distanse = String.valueOf(n.getNodeData().getDist());
            }
            if(n.getNodeData().getDist() == 0){
                tidligereNode = "start";
                distanse = "0";
            }
            System.out.println("Node: " + n.getNodeNumber() + " forrige node: " + tidligereNode + " distanse: " + distanse);
        }
    }
}
