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
 /*       String graphStrNodes = basePath + "noderNorden.txt";
        String graphStrEdge = basePath + "kanterNorden.txt";
        String graphStrInterest = basePath + "interessepktNorden.txt";*/
        BufferedReader br1 = new BufferedReader(new FileReader(new File(graphStrNodes)));
        BufferedReader br2 = new BufferedReader(new FileReader(new File(graphStrEdge)));
        BufferedReader br3 = new BufferedReader(new FileReader(new File(graphStrInterest)));


        graph.readNodeFile(br1);
        graph.readEdgeFile(br2);
        //graph.readInterestingData(br3);

        int[] startGoalNodeNumbers = graph.findStartGoalNodes("Oslo","Stockholm",br3);
        LinkedList<Node> shortestPath = graph.findDistance(startGoalNodeNumbers[0],startGoalNodeNumbers[1]);
        System.out.println("totDist: " + shortestPath.getLast().getDist());


        Date start = new Date();
        graph.findDistance(startGoalNodeNumbers[0],startGoalNodeNumbers[1]);
        Date slutt = new Date();
        double tid = (double)(slutt.getTime()-start.getTime());
        System.out.println(start);
        System.out.println(slutt);
        System.out.println("Millisekunder: " + (tid));

       /* while (shortestPath.size() > 0){
            System.out.println(shortestPath.removeFirst().getNodeNumber());
        }*/
    }


}
