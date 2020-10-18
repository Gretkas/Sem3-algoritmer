package oving7;

import java.io.IOException;

public class Klient {
    public static void main(String[] args) throws IOException {
        Huffmann hm = new Huffmann();
        hm.compress("/Users/sergiomartinez/Documents/algorithms/files/ov7/enwik8");
    }
}
