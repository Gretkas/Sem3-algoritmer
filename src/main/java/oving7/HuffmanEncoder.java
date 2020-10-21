package oving7;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public class HuffmanEncoder {
    private int[] frequencyArray;

    public HuffmanEncoder(int noCharacters) {
        this.frequencyArray = new int[noCharacters];
    }

    public HuffmanEncoded enCode(DataInputStream input){
        try {
            frequencyArray = fillFrequencyArray(input);
        } catch (IOException e) {
            for (int i = 0; i < frequencyArray.length; i++){
                System.out.println((char)i + " = " + frequencyArray[i]);
            }
            e.printStackTrace();
        }


        return null;
    }

    private Node createHuffmanTree(){
        PriorityQueue<Node> sortedQueue = new PriorityQueue<>();


        return null;
    }

    private int[] fillFrequencyArray(DataInputStream input) throws IOException {
        int nextChar = 1;
            while(nextChar != -1) {
                nextChar = (input.readByte());
                //System.out.println((char) nextChar);

                frequencyArray[nextChar&0xff]++;

            }

        return frequencyArray;
    }

    public String deCode(HuffmanEncoded result){
        return null;
    }

}
