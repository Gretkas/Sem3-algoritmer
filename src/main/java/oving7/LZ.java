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
    private StringBuffer sequences = new StringBuffer();

    public static void main(String[] args) throws IOException {
        LZ lz = new LZ();
        lz.compress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverse.txt");
        lz.decompress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseLZ.txt");
        //lz.readFile();
//        lz.decompress();
    }

    public void compress(String inputPath) throws IOException {
        String[] arr = inputPath.split("\\.");
        arr[arr.length-2] += "LZ";
        StringBuilder outputPath = new StringBuilder();
        for (int i = 0; i < arr.length-1; i++) {
            outputPath.append(arr[i]).append(".");
        }
        outputPath.append(arr[arr.length-1]);
        compress(inputPath, outputPath.toString());
    }

    public void decompress(String inputPath) throws IOException {
        String[] arr = inputPath.split("\\.");
        arr[arr.length-2] += "Decomp";
        StringBuilder outputPath = new StringBuilder();
        for (int i = 0; i < arr.length-1; i++) {
            outputPath.append(arr[i]).append(".");
        }
        outputPath.append(arr[arr.length-1]);
        decompress(inputPath, outputPath.toString());
    }

    public void decompress(String compressedFile, String output) throws IOException {
        sequences = new StringBuffer();
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(compressedFile)));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
        byte[] currentOutputBlock;

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        int byteIndex = 0;

        while (byteIndex < bFilArr.length) {
            int currentBlockLength = bFilArr[byteIndex++];
            byte currentByte;
            byte[] currentChar;
            int charLength = 1;

            if(currentBlockLength > 0){
                byte[] tempShort = {bFilArr[byteIndex++],bFilArr[byteIndex++]};
                short index = (short)(((tempShort[0] & 0xFF) << 8) | (tempShort[1] & 0xFF));    //her kan det være feil!!!
                StringBuilder currentSequence = new StringBuilder();
                System.out.println(byteIndex);
                for (int i = index; i <index+ currentBlockLength-1; i++) {
                    currentSequence.append(sequences.charAt(i));
                }
                currentByte = bFilArr[byteIndex++];

                if(currentByte >= 0){
                    currentChar = new byte[1];
                    currentChar[0] = currentByte;
                    currentSequence.append((char)currentChar[0]);
                }else {

                    String binaryString = Integer.toBinaryString(currentByte & 0xff);
                    for (int i = 1; i < 4; i++) {
                        if(binaryString.charAt(i) == 49) charLength++;
                        else break;
                    }
                    int tempIndex = 1;
                    currentChar = new byte[charLength];
                    currentChar[0] = currentByte;
                    while(tempIndex < charLength){
                        currentChar[tempIndex++] = bFilArr[byteIndex++];
                    }
                    currentSequence.append(new String(currentChar));
                }

                currentOutputBlock = currentSequence.toString().getBytes();
                sequences.append(currentSequence.toString());
            }
            else{
                int length = Math.abs(currentBlockLength);
                currentOutputBlock = new byte[length];

                StringBuilder currentSequence = new StringBuilder();

                for (int i = 0; i < length; i++) {
                    charLength = 1;
                    currentByte = bFilArr[byteIndex++];
                    if(currentByte >= 0) {

                        currentOutputBlock[i] = currentByte;
                        currentSequence.append((char)currentOutputBlock[i]);
                    }
                    else {
                        String binaryString = Integer.toBinaryString(currentByte & 0xff);
                        for (int j = 1; j < 4; j++) {
                            if (binaryString.charAt(j) == 49) charLength++;
                            else break;
                        }
                        int tempIndex = 1;
                        currentChar = new byte[charLength];
                        currentChar[0] = currentByte;
                        while (tempIndex < charLength) {
                            currentChar[tempIndex++] = bFilArr[byteIndex++];
                        }

                        currentSequence.append(new String(currentChar));
                    }
                }
                currentOutputBlock = currentSequence.toString().getBytes();
                sequences.append(currentSequence.toString());
            }


            utfil.write(currentOutputBlock);
            if(sequences.length() > 1<<15) trimList();
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }



    public void compress(String filePath, String outPath) throws IOException {
        sequences = new StringBuffer();
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outPath)));
        int prevIndex = -1;
        int indexDos;
        StringBuilder currentSequence = new StringBuilder();
        byte[] currentOutputBlock;

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        int byteIndex = 0;
        boolean lastUncompressed = false;
        StringBuffer uncompressedOut = new StringBuffer();
        while (byteIndex < bFilArr.length){

            byte currentByte = bFilArr[byteIndex++];
            byte[] currentChar ;
            int charLength = 1;

            if(currentByte >= 0){
                currentChar = new byte[1];
                currentChar[0] = currentByte;
                currentSequence.append((char)currentChar[0]);
            }else {

                String binaryString = Integer.toBinaryString(currentByte & 0xff);
                for (int i = 1; i < 4; i++) {
                    if(binaryString.charAt(i) == 49) charLength++;
                    else break;
                }
                int tempIndex = 1;
                currentChar = new byte[charLength];
                currentChar[0] = currentByte;
                while(tempIndex < charLength){
                    currentChar[tempIndex++] = bFilArr[byteIndex++];
                }
                currentSequence.append(new String(currentChar));
            }

            indexDos = sequences.indexOf(currentSequence.toString());

            if(indexDos < 0 || byteIndex == bFilArr.length || currentSequence.length() > 126){
                System.out.println(currentSequence);
                if (currentSequence.length() > 2) {
                    lastUncompressed = false;
                }
                sequences.append(currentSequence.toString());
                if (!lastUncompressed) {
                    byte[] currentSequenceBytes = uncompressedOut.toString().getBytes();
                    currentOutputBlock = new byte[currentSequenceBytes.length+1];
                    currentOutputBlock[0] = (byte) (-uncompressedOut.length() & 0xff);
                    for (int i = 0; i < currentSequenceBytes.length; i++) {
                        currentOutputBlock[i+1] = currentSequenceBytes[i];
                    }
                    utfil.write(currentOutputBlock);

                    uncompressedOut = new StringBuffer();
                }
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
                    utfil.write(currentOutputBlock);
                }else {
                    uncompressedOut.append(currentSequence.toString());
                    lastUncompressed = true;
                }
                //System.out.println(currentOutputBlock[0]);
                prevIndex = -1;
                currentSequence = new StringBuilder();
                if(sequences.length() > 1<<15) trimList();
            }else {
                prevIndex = indexDos;
            }
        }
        if (uncompressedOut.length()>0) {
            byte[] currentSequenceBytes = uncompressedOut.toString().getBytes();
            System.out.println(uncompressedOut.toString());
            currentOutputBlock = new byte[currentSequenceBytes.length+1];
            currentOutputBlock[0] = (byte) (-uncompressedOut.length() & 0xff);
            for (int i = 0; i < currentSequenceBytes.length; i++) {
                currentOutputBlock[i+1] = currentSequenceBytes[i];
            }
            utfil.write(currentOutputBlock);
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }


    private void trimList(){
        sequences.delete(0, sequences.length() - (1 << 15));
    }






}
