package oving5;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class KlientOv5 {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\robvo\\Desktop\\resources\\oving5\\L7g5.txt";

        Graph g = new Graph();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));


        g.newGraph(br,false);
        g.dfs();
        br = new BufferedReader(new FileReader(new File(path)));
        Graph reverse = new Graph();
        reverse.newGraph(br,true);
        ArrayList<ArrayList<Integer>> scc = reverse.dfsReverse(g.sortNodes());

        System.out.println("Grafen L7g5 har "+scc.size()+" sterkt sammenhengende komponenter:");
        for (int i = 0; i < scc.size(); i++) {
            System.out.println("Komponent: " + (i+1) + " Noder: " + scc.get(i).toString());
        }

    }
}
