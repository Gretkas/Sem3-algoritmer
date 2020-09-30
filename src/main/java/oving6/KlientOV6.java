package oving6;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

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
        String path = "C:\\Users\\robvo\\Desktop\\resources\\oving6\\vg1.txt";
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        Graph g = new Graph();
        g.newGraph(br);

        for (int i = 0; i < g.getPriorityQueue().length; i++) {
            System.out.println(g.getPriorityQueue()[i].toString());
            for (Edge e = g.getPriorityQueue()[i].getEdge();e!=null;e = e.getNextEdge()) {
                System.out.println(e.toString());
            }
        }


    }
}
