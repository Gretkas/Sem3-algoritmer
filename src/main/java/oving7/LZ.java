package oving7;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LZ {
    private byte[] bFilArr;
    private LinkedList<String> sequences = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        LZ lz = new LZ();
        //lz.compress();
        lz.readFile();
        //lz.decompress();
    }



    private void readFile() throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\testFil.txt")));

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        for (int i = 0; i < bFilArr.length; i++) {
            if(bFilArr[i] == 44) System.out.println();
            else System.out.println(Integer.toBinaryString(bFilArr[i] & 0xff));
        }
    }


    public void decompress() throws IOException {
        sequences = new LinkedList<>();
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\output\\test.txt")));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\output\\decomp.txt")));
        byte[] currentOutputBlock;

        while (innfil.available() > 0) {
            int currentBlockLength = innfil.readByte();

            if(currentBlockLength > 0){
                short index = innfil.readShort();
                StringBuilder currentSequence = new StringBuilder();

                currentSequence.append(sequences.get(index));
                currentSequence.append((char)innfil.readByte());
                currentOutputBlock = currentSequence.toString().getBytes();
                sequences.addLast(currentSequence.toString());
            }
            else{
                int length = Math.abs(currentBlockLength);
                currentOutputBlock = new byte[length];
                StringBuilder currentSequence = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    currentOutputBlock[i] = innfil.readByte();
                    currentSequence.append((char)currentOutputBlock[i]);
                }
                sequences.addLast(currentSequence.toString());
            }


            utfil.write(currentOutputBlock);
            if(sequences.size() > 1<<15) trimList();
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }



    public void compress() throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\testFil.txt")));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("C:\\Users\\robvo\\Desktop\\resources\\oving7\\output\\test.txt")));
        int prevIndex = -1;
        int indexDos;
        StringBuilder currentSequence = new StringBuilder();
        byte[] currentOutputBlock;

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        int byteIndex = 0;

        while (byteIndex < bFilArr.length){

            byte currentByte = bFilArr[byteIndex];
            byteIndex++;
            byte[] currentChar ;
            int charLength = 1;

            if(currentByte >= 0){
                currentChar = new byte[1];
                currentChar[0] = currentByte;
                currentSequence.append((char)currentChar[0]);
            }else {
                currentChar = new byte[4];
                currentChar[0] = currentByte;
                while(bFilArr[byteIndex] < 0){
                    currentChar[charLength] = bFilArr[byteIndex];
                    byteIndex++;
                    charLength++;
                }
                currentSequence.append(new String(currentChar));
            }

            indexDos = sequences.indexOf(currentSequence.toString());


            if(indexDos < 0){
                sequences.add(currentSequence.toString());
                if(currentSequence.length() > 2){
                    currentOutputBlock = new byte[3+charLength];

                    currentOutputBlock[0] = (byte) (currentSequence.length()  & 0xff);
                    currentOutputBlock[1] = (byte) (prevIndex>>8);
                    currentOutputBlock[2] = (byte) (prevIndex & 0b11111111);
                    if(charLength == 1){
                        currentOutputBlock[3] = (byte) (currentChar[0] & 0xff);
                    }
                    else{
                        for (int i = 3; i < currentOutputBlock.length; i++) {
                            currentOutputBlock[i] = currentChar[i-3];
                        }
                    }
                }else {
                    byte[] currentSequenceBytes = currentSequence.toString().getBytes();

                    currentOutputBlock = new byte[currentSequenceBytes.length+1];
                    currentOutputBlock[0] = (byte) (-currentSequence.length() & 0xff);
                    for (int i = 0; i < currentSequenceBytes.length; i++) {
                        currentOutputBlock[i+1] = currentSequenceBytes[i];
                    }
                }
                //System.out.println(currentOutputBlock[0]);
                utfil.write(currentOutputBlock);
                prevIndex = -1;
                currentSequence = new StringBuilder();
                if(sequences.size() > 1<<15) trimList();
            }else {
                prevIndex = indexDos;
            }
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }


    private void trimList(){
         sequences.removeFirst();
    }






}
