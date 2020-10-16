package oving7;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class LZ {
    private byte[] bFilArr;
    private ArrayList<String> sequences = new ArrayList<>(1<<15);

    public static void main(String[] args) throws IOException {
        LZ lz = new LZ();
        lz.compress();
        Integer test = -1;
        System.out.println(test.byteValue());
    }



    private void readFile() throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\testFil.txt")));

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
    }

    public void compress() throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\testFil.txt")));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\output\\test.txt")));
        int prevIndex = -1;
        int indexDos;
        StringBuilder currentSequence = new StringBuilder();
        byte[] currentOutputBlock;


        while (innfil.available() > 0){

            char currentChar = (char) innfil.readByte();
            currentSequence.append(currentChar);
            indexDos = sequences.indexOf(currentSequence.toString());
            if(indexDos < 0){
                sequences.add(currentSequence.toString());
                if(currentSequence.length() > 2){
                    currentOutputBlock = new byte[4];
                    currentOutputBlock[0] = (byte) (currentSequence.length()  & 0xff);
                    currentOutputBlock[1] = (byte) (prevIndex>>8);
                    currentOutputBlock[2] = (byte) (prevIndex & 0b11111111);
                    currentOutputBlock[3] = (byte) (currentChar & 0xff);
                }else {
                    currentOutputBlock = new byte[currentSequence.length()+1];
                    currentOutputBlock[0] = (byte) (-currentSequence.length() & 0xff);
                    byte[] outputChar = currentSequence.toString().getBytes();
                    for (int i = 0; i < outputChar.length; i++) {
                        currentOutputBlock[i+1] = outputChar[i];
                    }
                }
                System.out.println(currentOutputBlock[1]);
                utfil.write(currentOutputBlock);
                prevIndex = -1;
                currentSequence = new StringBuilder();
            }else {
                prevIndex = indexDos;
            }
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }
}
