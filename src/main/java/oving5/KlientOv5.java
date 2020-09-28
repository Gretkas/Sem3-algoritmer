package oving5;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class KlientOv5 {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\robvo\\Desktop\\resources\\oving5\\L7Skandinavia-navn.txt";

        Graph g = new Graph();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));


        g.newGraph(br);
        g.dfs();
        br = new BufferedReader(new FileReader(new File(path)));
        Graph reverse = new Graph();
        reverse.newFlippedGraph(br);
        ArrayList<ArrayList<Integer>> dfs = reverse.dfsReverse(g.sortNodes());
        for (int i = 0; i < dfs.size(); i++) {
            System.out.println(dfs.get(i).toString());
        }

        //System.out.println(Arrays.toString(g.getNodes()));
    }
}
