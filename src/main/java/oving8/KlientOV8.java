package oving8;


import java.io.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.jar.JarOutputStream;

public class KlientOV8 {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        GraphAStar gas = new GraphAStar();
        String basePath = "C:\\Users\\Robin Vold\\OneDrive\\Skole\\NTNU\\Algoritmer og datastrukturer\\resources\\oving8\\";
/*      String graphStrNodes = basePath + "noderIslam.txt";
        String graphStrEdge = basePath + "kanterIslam.txt";
        String graphStrInterest = basePath + "interessepktIslam.txt";*/
        String graphStrNodes = basePath + "noderNorden.txt";
        String graphStrEdge = basePath + "kanterNorden.txt";
        String graphStrInterest = basePath + "interessepktNorden.txt";
        BufferedReader br1 = new BufferedReader(new FileReader(new File(graphStrNodes)));
        BufferedReader br2 = new BufferedReader(new FileReader(new File(graphStrEdge)));
        BufferedReader br3 = new BufferedReader(new FileReader(new File(graphStrInterest)));



        Date start;
        Date slutt;
        double tid;
        int cases = 1;
        switch (cases){
            case 0:
                gas.readNodeFile(br1);
                gas.readEdgeFile(br2);
                int startIndex = gas.findStartGoalNodes("Trondheim","Helsinki",br3);
                start = new Date();
                LinkedList<NodeAStar> shortestPathGas = gas.findDistance(startIndex);
                slutt = new Date();
                tid = (double)(slutt.getTime()-start.getTime());
                System.out.println("Antall noder gått gjennom: " + gas.getNumberNodesChecked());    //410391
                printTravelTime(shortestPathGas.getLast().getStartDist());
                System.out.println("Kjøretid i millisekunder: " + (tid));      //1079.0
                Map.generateMapGAS(shortestPathGas);
                break;
            case 1:
                graph.readNodeFile(br1);
                graph.readEdgeFile(br2);
                int[] startGoalNodeNumbers = graph.findStartGoalNodes("Trondheim","Helsinki",br3);
                start = new Date();
                LinkedList<Node> shortestPath = graph.findDistance(startGoalNodeNumbers[0],startGoalNodeNumbers[1]);
                slutt = new Date();
                tid = (double)(slutt.getTime()-start.getTime());
                System.out.println("Antall noder gått gjennom: " + graph.getNumberNodesChecked());  //823568
                printTravelTime(shortestPath.getLast().getDist());
                System.out.println("Kjøretid i millisekunder: " + (tid));      //2448.0
                Map.generateMap(shortestPath);
                break;
            case 2:
                graph.readNodeFile(br1);
                graph.readEdgeFile(br2);
                int startNode = graph.readNodeCodes(br3,"Røros hotell");
                Node[] nodes = graph.dijkstraGas(startNode,4);
                Map.generateMap(nodes,false);
                break;
            default:
                System.out.println("Skriv in 0-2");
        }
    }

    private static void printTravelTime(int distanceInCentiSeconds){
        int seconds = distanceInCentiSeconds / 100;
        int hours = seconds / 3600;
        seconds %= 3600;
        int minutes = seconds / 60;
        seconds %= 60;
        System.out.println("Travel time: hours: "+hours+", minutes: "+minutes+", seconds: "+seconds);
    }




}
