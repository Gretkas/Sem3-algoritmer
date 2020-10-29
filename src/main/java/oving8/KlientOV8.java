package oving8;


import java.io.*;
import java.util.Date;
import java.util.LinkedList;

public class KlientOV8 {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        String basePath = "C:\\Users\\robvo\\Desktop\\resources\\oving8\\";
      String graphStrNodes = basePath + "noderIslam.txt";
        String graphStrEdge = basePath + "kanterIslam.txt";
        String graphStrInterest = basePath + "interessepktIslam.txt";
/*        String graphStrNodes = basePath + "noderNorden.txt";
        String graphStrEdge = basePath + "kanterNorden.txt";
        String graphStrInterest = basePath + "interessepktNorden.txt";*/
        BufferedReader br1 = new BufferedReader(new FileReader(new File(graphStrNodes)));
        BufferedReader br2 = new BufferedReader(new FileReader(new File(graphStrEdge)));
        BufferedReader br3 = new BufferedReader(new FileReader(new File(graphStrInterest)));




        GraphAStar gas = new GraphAStar();
        gas.readNodeFile(br1);
        gas.readEdgeFile(br2);
        int startIndex = gas.findStartGoalNodes("Hellissandur","Orkan",br3);
        LinkedList<NodeAStar> shortestPathGas = gas.findDistance(startIndex);//Her er feilen!!!!
        System.out.println(shortestPathGas.size());
        System.out.println(shortestPathGas.removeFirst().getNodeNumber());
        Map.generateMapGAS(shortestPathGas);



/*        graph.readNodeFile(br1);
        graph.readEdgeFile(br2);
        int[] startGoalNodeNumbers = graph.findStartGoalNodes("Trondheim","Alta",br3);
        LinkedList<Node> shortestPath = graph.findDistance(startGoalNodeNumbers[0],startGoalNodeNumbers[1]);
        System.out.println("totDist: " + shortestPath.getLast().getDist());
        Map.generateMap(shortestPath);*/


/*        Date start = new Date();
        graph.findDistance(startGoalNodeNumbers[0],startGoalNodeNumbers[1]);
        Date slutt = new Date();
        double tid = (double)(slutt.getTime()-start.getTime());
        System.out.println(start);
        System.out.println(slutt);
        System.out.println("Millisekunder: " + (tid));*/

/*        int startNode = graph.readNodeCodes(br3,"Trondheim");
        Node[] nodes = graph.dijkstraGas(startNode,2);
        Map.generateMap(nodes,false);*/
    }


}
