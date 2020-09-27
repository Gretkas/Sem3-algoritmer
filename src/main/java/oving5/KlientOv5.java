package oving5;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class KlientOv5 {

    public static void main(String[] args) throws IOException {

        Graph g = new Graph();
        System.out.println(new File("C:\\Users\\robvo\\Desktop\\resources\\oving5\\L7g1.txt"));
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\robvo\\Desktop\\resources\\oving5\\L7g1.txt")));


        g.newGraph(br);
        ArrayList<ArrayList<Integer>> dfs = g.dfs();
        for (int i = 0; i < dfs.size(); i++) {
            System.out.println(dfs.get(i).toString());
        }
        //System.out.println(Arrays.toString(g.getNodes()));
    }
}
