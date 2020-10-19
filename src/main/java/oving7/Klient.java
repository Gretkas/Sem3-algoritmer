package oving7;

import java.io.IOException;
import java.util.Arrays;

public class Klient {
    public static void main(String[] args) throws IOException {
        Huffmann hm = new Huffmann();
        hm.compress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\testFil.txt");
        hm.deCompress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\testFilHM.txt");

    }
}
