package oving7;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream("./diverse.txt")));

        HuffmanEncoder encoder = new HuffmanEncoder(256);
        encoder.enCode(dataStream);
    }
}
